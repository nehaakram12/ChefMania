package com.app.chefmania.chefmania.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthController {

    private static final String SHARED_PREF_FILE = "authdetails";
    private static final String SHARED_PREF_USER_TYPE = "userType";

    private OnSignInCompleteListener mOnSignInCompleteListener;

    private Context mContext;

    public static final int TYPE_USER = 0;
    public static final int TYPE_ADMIN = 1;

    public interface OnSignInCompleteListener {
        void onUserLoggedIn();
        void onAdminLoggedIn();
        void onFailed(Exception ex);
        void noSuchUserEnrolledInOrg();
        void onNetworkFailed();
    }

    public AuthController(Context context) {
        mContext = context;
    }

    public static void setLoggedInUserType(Context context, int userType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREF_USER_TYPE, userType).apply();
    }

    public static int getLoggedInUserType(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SHARED_PREF_USER_TYPE, -1);
    }

    public void signIn(String email, String pass, OnSignInCompleteListener listener) {
        mOnSignInCompleteListener = listener;

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.exists()) {
                                    mOnSignInCompleteListener.noSuchUserEnrolledInOrg();
                                }
                                int type = (int) (long) dataSnapshot.getValue();
                                setLoggedInUserType(mContext, type);
                                switch (type) {
                                    case 0:
                                        mOnSignInCompleteListener.onUserLoggedIn();
                                        break;
                                    case 1:
                                        mOnSignInCompleteListener.onAdminLoggedIn();
                                        break;
                                    default:
                                        mOnSignInCompleteListener.onUserLoggedIn();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                mOnSignInCompleteListener.onFailed(databaseError.toException());
                            }
                        }))
                .addOnFailureListener(e -> {
                    try {
                        throw e;
                    } catch (FirebaseNetworkException fnex) {
                        mOnSignInCompleteListener.onNetworkFailed();
                    } catch (Exception ex) {
                        mOnSignInCompleteListener.onFailed(ex);
                    }
                });
    }

}

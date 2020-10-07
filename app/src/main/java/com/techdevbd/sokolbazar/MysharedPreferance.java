package com.techdevbd.sokolbazar;

import android.content.Context;
import android.content.SharedPreferences;

public class MysharedPreferance {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor ;
    private static MysharedPreferance mysharedPreferance=null;

    private MysharedPreferance(Context context)
    {
        sharedPreferences = context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }


  public static synchronized MysharedPreferance getPreferences(Context context)
  {

      if(mysharedPreferance==null) {
          mysharedPreferance = new MysharedPreferance(context);
      }

      return mysharedPreferance;
  }


    public void setName(String name)
    {
        editor.putString("uname",name);
        editor.apply();
    }

    public String getName()
    {
        return sharedPreferences.getString("uname","none");
    }


    public void setPhone(String phone)
    {
        editor.putString("uphone",phone);
        editor.apply();
    }

    public String getPhone()
    {
        return sharedPreferences.getString("uphone","none");
    }

    public void setAddress(String address)
    {
        editor.putString("uaddress",address);
        editor.apply();
    }

    public String getAddress()
    {
        return sharedPreferences.getString("uaddress","none");
    }

}

package com.demo.test;

import java.util.ArrayList;
import java.util.List;

public class TryDemo {


    public static void main(String[] args){

        List<String> names = new ArrayList<String>();
        names.add( "Mark" );
        names.add( "Jack" );
        names.add( "FangFang" );

        String str = getRequestString(names);

        System.out.print( "str===="+str );




    }

    private static String getRequestString(List<String> names) {
        if(names==null || names.size()==0){
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(  );
        for (String name : names) {
            stringBuffer.append( name );
        }
        return stringBuffer.toString();
    }
}

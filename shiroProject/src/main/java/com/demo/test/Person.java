package com.demo.test;

public class Person {


    public static final String LILI = "Lili";

    private String xlc;

    private int age;

    public void setName(String xlc) {
        this.xlc = xlc;
    }

    public void setAge(int age) {
        this.age = age;
    }


    private String user;

    public String getUser(){
        return user;
    }

    public void setUser(String user){
        this.user = user;
    }



    public String getUserName(String mark, String name){

        System.out.print( "name:"+ name );
        String username = user + name;


        return username;



    }

    public static void main(String[] args){
        Person person = new Person();
        person.setUser( "Lili " );
        String mark = "Mark";

        vailde( person, mark );

        vailde( person, mark );

        person.getUserName( mark, LILI );



    }

    private static void vailde(Person person, String mark) {
        person.getUserName( mark, LILI );
        person.getUserName( mark, LILI );
    }


}

package com.elance.utils;

import java.io.File;

/**
 * Date: 31.08.14
 * Time: 1:13
 */
public class FileUtils {
    public static void deleteFile(String name){
        try{

            File file = new File(name);

            if(file.delete()){
                System.out.println(file.getName() + " is deleted as no keywords matched");
            }else{
                System.out.println("Delete operation is failed.");
            }

        }catch(Exception e){
            System.out.println("Delete operation is failed.");
        }
    }
}

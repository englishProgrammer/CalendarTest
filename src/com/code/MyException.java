package com.code;

import org.fusesource.jansi.Ansi;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;
/**
 * @Author:LUJIPENG
 * @Description:
 * @Date:Created in 15:37 2017/11/20
 * @Modified By:
 */
public class MyException extends RuntimeException {
    String message;
    MyException(String m){message = m;}
    void show(){
        System.out.println(ansi().eraseScreen().fg(YELLOW).a(message).reset());
    }
}

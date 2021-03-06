package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Transfer
{
    public static void go(InputStream in, OutputStream out, boolean closeOnExit) throws IOException
    {
        byte buf[] = new byte[1024];
       
        int n;
        while((n=in.read(buf))!=-1)
            out.write(buf,0,n);

        out.write(0);

        if (closeOnExit)
        {
            in.close();
            out.close();
        }
    }
}
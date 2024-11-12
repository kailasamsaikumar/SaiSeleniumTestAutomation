package com.inspify.utilities;

import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecutor {

    public static void execute(String command) throws IOException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        if(SystemUtils.IS_OS_LINUX){ processBuilder.command("bash", "-c", command); }
        if(SystemUtils.IS_OS_WINDOWS){ processBuilder.command("cmd", "/C", command); }

        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
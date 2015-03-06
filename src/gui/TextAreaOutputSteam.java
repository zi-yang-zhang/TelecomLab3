package gui;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ZiYang on 2015-03-05.
 */
public class TextAreaOutputSteam extends OutputStream {
    private JTextArea textArea;
    public TextAreaOutputSteam(JTextArea textArea){
        this.textArea = textArea;
    }
    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char) b));
    }
}

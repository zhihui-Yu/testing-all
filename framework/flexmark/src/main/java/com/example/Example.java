package com.example;

import com.example.utils.FileReadUtils;
import com.example.utils.MarkDown2HtmlUtils;

import java.io.IOException;

/**
 * @author simple
 */
public class Example {
    public static void main(String[] args) throws IOException {
        System.out.println(MarkDown2HtmlUtils.markdown2Html(FileReadUtils.read("text.md")));
    }
}

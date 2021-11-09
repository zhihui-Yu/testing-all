package com.example;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


public class ConvertEncoding {

    public static void main(String[] args) {
        convertCharset("F:\\eclipse_Mars\\base", Charset.forName("GBK"), StandardCharsets.UTF_8, "java");

    }

    /**
     * 转换文件编码格式
     *
     * @param path        需要转换的文件或文件夹路径
     * @param fromCharset 原编码格式
     * @param toCharset   目标编码格式
     * @param expansion   需要转换的文件扩展名,如需全部转换则传 null
     */
    private static void convertCharset(String path, final Charset fromCharset, final Charset toCharset, final String expansion) {
        if (StrUtil.isBlank(path)) {
            return;
        }
        File file = FileUtil.file(path);
        File[] listFiles = file.listFiles(pathname -> {
            if (StrUtil.isBlank(expansion)) {
                return true;
            }
            if (FileUtil.isDirectory(pathname) || FileUtil.extName(pathname).equals("java")) {
                return true;
            }
            return false;
        });
        for (int i = 0; i < Objects.requireNonNull(listFiles).length; i++) {
            if (listFiles[i].isDirectory()) {
                final String canonicalPath = FileUtil.getCanonicalPath(listFiles[i]);
                //每个文件夹分个线程处理,提高点儿效率
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        convertCharset(canonicalPath, fromCharset, toCharset, expansion);
                    }
                }).start();
            } else {
                FileUtil.convertCharset(listFiles[i], fromCharset, toCharset);
                Console.log("转换完成文件名:{}", listFiles[i].getName());
            }
        }
    }
}
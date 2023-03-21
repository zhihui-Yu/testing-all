package com.test;

import uk.co.caprica.vlcj.player.base.Logo;
import uk.co.caprica.vlcj.player.base.LogoPosition;
import uk.co.caprica.vlcj.player.base.Marquee;
import uk.co.caprica.vlcj.player.base.MarqueePosition;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author simple
 */
public class Tutorial {

    private static Tutorial thisApp;

    private final JFrame frame;

    private final EmbeddedMediaPlayerComponent mediaPlayerComponent; // 内嵌是通过使用三方的来达到目的 https://www.videolan.org/vlc/#download

    public static void main(String[] args) {
        thisApp = new Tutorial();
    }

    public Tutorial() {
        frame = new JFrame("My First Media Player");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });

//        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
//        frame.setContentPane(mediaPlayerComponent);


        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

        JPanel controlsPane = new JPanel();
        var pauseButton = new JButton("Pause");
        controlsPane.add(pauseButton);
        pauseButton.addActionListener(e -> {
            mediaPlayerComponent.mediaPlayer().controls().pause();
        });

        var rewindButton = new JButton("Rewind");
        controlsPane.add(rewindButton);

        rewindButton.addActionListener(e -> {
            mediaPlayerComponent.mediaPlayer().controls().skipTime(-10000); // 后退 xxx  ms
        });

        var skipButton = new JButton("Skip");
        controlsPane.add(skipButton);
        skipButton.addActionListener(e -> {
            mediaPlayerComponent.mediaPlayer().controls().skipTime(10000); //  前进 xxx ms
        });

        Marquee marquee = Marquee.marquee() // 字幕一样，可设置时间
            .text("vlcj tutorial")
            .size(40)
            .colour(Color.WHITE)
            .timeout(3000)
            .position(MarqueePosition.BOTTOM_RIGHT)
            .opacity(0.8f)
            .enable();
        mediaPlayerComponent.mediaPlayer().marquee().set(marquee);

        // 禁用字幕
        mediaPlayerComponent.mediaPlayer().marquee().enable(false);


        // 在视频上叠加一个徽标, 没效果
        Logo logo = Logo.logo()
            .file("img.png")
            .position(LogoPosition.CENTRE)
            .opacity(0.5f)
            .enable();
        mediaPlayerComponent.mediaPlayer().logo().set(logo);
//        mediaPlayerComponent.mediaPlayer().logo().enable(false);


        contentPane.add(controlsPane, BorderLayout.SOUTH);

        frame.setContentPane(contentPane);
        frame.setVisible(true);

        mediaPlayerComponent.mediaPlayer().media().play("C:\\Users\\simple\\IdeaProjects\\JavaBase\\oceans.mp4");
    }
}
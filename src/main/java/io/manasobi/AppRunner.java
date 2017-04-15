package io.manasobi;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import io.manasobi.view.AppView;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by tw.jang on 2017-04-13.
 */

@SpringBootApplication
public class AppRunner extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launchApp(AppRunner.class, AppView.class, args);
    }
}

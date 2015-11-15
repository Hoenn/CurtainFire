package com.neet.cf.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.neet.cf.CurtainFire;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(720, 576);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new CurtainFire(720, 576);
        }
}
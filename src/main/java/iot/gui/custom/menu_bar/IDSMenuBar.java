package iot.gui.custom.menu_bar;

import iot.gui.custom.menu_bar.DeviceMenuBar;

import javax.swing.*;

public class IDSMenuBar extends JMenuBar {
    public IDSMenuBar() {
        super();
        this.init();
    }
    private void init() {
        this.add(new DeviceMenuBar());
    }
}

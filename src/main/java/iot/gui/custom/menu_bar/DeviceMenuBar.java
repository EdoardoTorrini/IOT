package iot.gui.custom.menu_bar;

import iot.gui.custom.dialog.GetDeviceDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeviceMenuBar extends JMenu {
    public DeviceMenuBar() {
        super("Device");
        this.init();
    }

    private void init() {
        JMenuItem getDevice = new JMenuItem("Get device");
        getDevice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GetDeviceDialog deviceDialog = new GetDeviceDialog();
                deviceDialog.setVisible(true);
            }
        });
        this.add(getDevice);

        JMenuItem updateDevice = new JMenuItem("Update device");
        updateDevice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.add(updateDevice);
    }
}

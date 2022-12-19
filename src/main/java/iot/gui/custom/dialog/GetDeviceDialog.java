package iot.gui.custom.dialog;

import iot.ids.client.DeviceCoapClient;
import iot.ids.model.DeviceModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class GetDeviceDialog extends JDialog {

    public GetDeviceDialog() {
        super();
        this.init();
    }
    private void init() {
        this.setTitle("Device Information");
        this.setSize(300, 300);

        HashMap<String, DeviceModel> mDeviceMap = new HashMap<>();

        // List box with all the device id
        JComboBox<String> cbDeviceId = new JComboBox<>();
        try {
            mDeviceMap = new DeviceCoapClient().getDeviceList();
        } catch (Exception eErr) {
            // TODO: forza chiusura
        }
        for(Map.Entry<String, DeviceModel> record : mDeviceMap.entrySet())
            cbDeviceId.addItem(record.getValue().getDeviceId());

        cbDeviceId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = 0;
            }
        });

        this.add(cbDeviceId, BorderLayout.NORTH);
        this.add(new JPanel(), BorderLayout.CENTER);
        this.add(new JPanel(), BorderLayout.SOUTH);
    }
}

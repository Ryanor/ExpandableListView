package at.gwu.expandable.expandablelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<Service> serviceList;
    private HashMap<Service, List<Characteristic>> characteristicList;

    public ExpandableListViewAdapter(Context context, List<Service> serviceList, HashMap<Service, List<Characteristic>> characteristicList) {
        this.context = context;
        this.serviceList = serviceList;
        this.characteristicList = characteristicList;
    }

    @Override
    public int getGroupCount() {
        return serviceList.size();
    }

    @Override
    public int getChildrenCount(int serviceListPosition) {
        return characteristicList.get(serviceList.get(serviceListPosition)).size();
    }

    @Override
    public Object getGroup(int serviceListPosition) {
        return serviceList.get(serviceListPosition);
    }

    @Override
    public Object getChild(int serviceListPosition, int characteristicListPosition) {
        return characteristicList.get(serviceList.get(serviceListPosition))
                .get(characteristicListPosition);
    }

    @Override
    public long getGroupId(int serviceListPosition) {
        return serviceListPosition;
    }

    @Override
    public long getChildId(int serviceListPosition, int characteristicListPosition) {
        return characteristicListPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int serviceListPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.expandablelist_group_item, null);
        }

        TextView serviceUUID = view.findViewById(R.id.service_uuid);
        Service service = serviceList.get(serviceListPosition);
        serviceUUID.setText(service.getUUID());

        return view;
    }

    @Override
    public View getChildView(int serviceListPosition, int characteristicListPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.expandablelist_list_item, null);
        }

        Characteristic characteristic = (Characteristic) getChild(serviceListPosition, characteristicListPosition);

        TextView characteristicUUID = view.findViewById(R.id.characteristic_uuid);
        TextView characteristicValue = view.findViewById(R.id.characteristic_value);
        TextView characteristicDescriptorCount = view.findViewById(R.id.characteristic_descriptor_count);

        characteristicUUID.setText(characteristic.getUUID());
        characteristicValue.setText(characteristic.getValue());
        characteristicDescriptorCount.setText(characteristic.getAmountOfDescrptors());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

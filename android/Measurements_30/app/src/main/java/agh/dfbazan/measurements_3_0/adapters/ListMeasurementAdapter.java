package agh.dfbazan.measurements_3_0.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.model.Measurement;

public class ListMeasurementAdapter extends ArrayAdapter<Measurement> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private String unitValue;

    private static class ViewHolder {
        TextView date;
        TextView value;
        TextView unit;
    }

    public ListMeasurementAdapter(Context context, int resource, ArrayList<Measurement> objects, String unitValue) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.unitValue = unitValue;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        String date = formatter.format(getItem(position).getMeasDate());
        String value = getItem(position).getMeasValue();
        String unit = unitValue;

        final View result;

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.value = (TextView) convertView.findViewById(R.id.value);
            holder.unit = (TextView) convertView.findViewById(R.id.unitDeviceM);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.date.setText(date);
        holder.value.setText(value);
        holder.unit.setText(unit);

        return convertView;
    }

}


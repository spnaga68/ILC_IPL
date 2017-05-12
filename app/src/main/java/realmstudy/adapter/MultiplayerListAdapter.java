package realmstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmResults;
import realmstudy.R;
import realmstudy.data.RealmObjectData.Player;

/**
 * Created by developer on 18/2/17.
 */
public class MultiplayerListAdapter extends BaseAdapter {
    RealmResults<Player> data;
    Context c;
    ArrayList<Integer> selected_data = new ArrayList<>();

    private ArrayList<Boolean> checkedList;

    public MultiplayerListAdapter(Context c, RealmResults<Player> data) {
        this.data = data;
        this.c = c;
        checkedList = new ArrayList<>();
        for (Player p : data)
            checkedList.add(false);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    private static class ViewHolder {
        public TextView title, ph_no;
        public CheckBox multi_player_checkbox;
        public RelativeLayout overall_lay;


    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.player_multi_select_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.playername);
            viewHolder.ph_no = (TextView) convertView.findViewById(R.id.time);
            viewHolder.multi_player_checkbox = (CheckBox) convertView.findViewById(R.id.multi_player_checkbox);
            viewHolder.overall_lay = (RelativeLayout) convertView.findViewById(R.id.overall_lay);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Player obj = data.get(position);
        viewHolder.title.setText(obj.getName());
        viewHolder.ph_no.setText(obj.getPh_no());
        viewHolder.multi_player_checkbox.setTag(position);
        viewHolder.overall_lay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                (view).findViewById(R.id.multi_player_checkbox).performClick();
            }
        });
        viewHolder.multi_player_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b)
//                    selected_data.add(data.get((int) compoundButton.getTag()).getpID());
//                else
//                    selected_data.remove((data.get((int) compoundButton.getTag()).getpID()));
                checkedList.set((int) compoundButton.getTag(), b);
            }
        });
        viewHolder.multi_player_checkbox.setChecked(checkedList.get(position));
        return convertView;
    }


    public ArrayList<Integer> selectedPlayersID() {
        selected_data.clear();
        for (int i = 0; i < checkedList.size(); i++) {
            if (checkedList.get(i))
                selected_data.add(data.get(i).getpID());
        }

        return selected_data;
    }
}

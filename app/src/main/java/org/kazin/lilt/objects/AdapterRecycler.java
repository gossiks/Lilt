package org.kazin.lilt.objects;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.romainpiel.shimmer.ShimmerTextView;

import org.kazin.lilt.R;

/**
 * Created by Alexey on 08.09.2015.
 */
public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolderRecycler> {
    @Override
    public AdapterRecycler.ViewHolderRecycler onCreateViewHolder(ViewGroup viewGroup, int i) {

        return null;
    }

    @Override
    public void onBindViewHolder(AdapterRecycler.ViewHolderRecycler viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }








    //ViewHolder Types

    //Mother-class
    public static class  ViewHolderRecycler extends RecyclerView.ViewHolder{

        public jEvent event;
        public int eventType;

        public ViewHolderRecycler(View itemView, jEvent eventIn) {
            super(itemView);
            event = eventIn;
            eventType = eventIn.getType();
        }

        public jEvent getEvent(){
            return event;
        }

        public int getEventType(){
            return eventType;
        }
    }

    //Sun classes

    public static class ViewHolderTelephone extends ViewHolderRecycler{
        public ShimmerTextView textView;
        public Button button;

        public ViewHolderTelephone(View itemView, jEvent eventIn) {
            super(itemView, eventIn);
            textView = (ShimmerTextView) itemView.findViewById(R.id.card_telephone_activity_main);
            button = (Button) itemView.findViewById(R.id.card_change_telephone_activity);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    event.onEvent(null);
                }
            });
        }
    }

    public static class ViewHolderRingtone extends ViewHolderRecycler{

        public TextView ringtoneText;
        public Button button;
        public ProgressBar progressBar;

        public ViewHolderRingtone(View itemView, final jEvent eventIn) {
            super(itemView, eventIn);

            ringtoneText = (TextView) itemView.findViewById(R.id.ringtone_main);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar_ringtone_upload_mainactivity);
            button = (Button) itemView.findViewById(R.id.change_ringtone_main);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    event.onEvent(null);
                }
            });
        }
    }

    public static  class ViewerHolderSetAllRingtones extends ViewHolderRecycler{

        public SwitchCompat switchCompat;
        public ShimmerTextView setAllRingtonesText;
        public ProgressBar progressBar;

        public ViewerHolderSetAllRingtones(View itemView, jEvent eventIn) {
            super(itemView, eventIn);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.card_switch_set_all_ringtones_activity_main);
            setAllRingtonesText = (ShimmerTextView) itemView.findViewById(R.id.card_set_all_telephones_label__activity_main);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar_ringtone_upload_mainactivity);
        }
    }
}

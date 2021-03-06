package org.kazin.lilt.main.main;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.kazin.lilt.R;
import org.kazin.lilt.objects.ContactForSettings;
import org.kazin.lilt.objects.jEvent;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;

/**
 * Created by Alexey on 09.09.2015.
 */
public class CardAdapterMain extends CardArrayAdapter{
    //const
    public static int CARD_TYPE_TELEPHONE = 0;
    public static int CARD_TYPE_RINGTONE = 1;
    public static int CARD_TYPE_UPDATE_RINGTONES = 2;
    public static int CARD_TYPE_SETTINGS = 3;

    public CardAdapterMain(Context context, List<Card> cards) {
        super(context, cards);
    }

    @Override
    public int getViewTypeCount() {
        return 4; //number of CARD_TYPE static int in the beginning of file
    }


    //custom cards

    public static class CardTelephone extends Card{

        jEvent event;

        public CardTelephone(Context context, jEvent event) {
            super(context);
            this.event = event;
            init();
        }

        private void init(){
            CardHeader header = new CardHeader(getContext());
            header.setTitle("Telephone");

            addCardHeader(header);

            CardExpandTelephone cardExpand = new CardExpandTelephone(getContext(), event);
            addCardExpand(cardExpand);
        }

        @Override
        public int getType() {
            return CARD_TYPE_TELEPHONE;
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            super.setupInnerViewElements(parent, view);
            setViewToClickToExpand(ViewToClickToExpand.builder().setupView(parent));
        }

        //expand class

        private class CardExpandTelephone extends CardExpand {
            jEvent event;

            public CardExpandTelephone(Context context, jEvent event) {
                super(context, R.layout.card_expand_telephone_activity_main);
                this.event = event;
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View view) {
                Button changeTelephone = (Button) view.findViewById(R.id.button_change_telephone);
                changeTelephone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        event.onEvent(null);
                    }
                });
            }
        }
    }

    public static class CardRingtone extends Card {

        jEvent event;
        ShimmerTextView textView;
        Shimmer animator;

        public CardRingtone(Context context, jEvent event) {
            super(context, R.layout.card_inner_ringtone);
            this.event = event;
            init();
        }

        private void init(){
            CardHeader header = new CardHeader(getContext());
            header.setTitle("Ringtone");
            addCardHeader(header);

            setTitle("not defined yet");

            addCardExpand(new CardExpandRingtone(getContext(), event));

            animator = new Shimmer();
            animator.setDuration(3000);
        }

        @Override
        public int getType() {
            return CARD_TYPE_RINGTONE;
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            textView = (ShimmerTextView) view;
            setViewToClickToExpand(ViewToClickToExpand.builder().setupView(parent));
        }

        //effects for text view
        public void setText(String text){
            textView.setText(text);
        }

        public void shimmerStart(){
            animator.start(textView);
        }

        public void shimmerStop(){
            animator.cancel();
        }

        private class CardExpandRingtone extends CardExpand{
            jEvent event;

            public CardExpandRingtone(Context context, jEvent event) {
                super(context, R.layout.card_expand_ringtone);
                this.event = event;
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View view) {
                Button changeRingtone = (Button) view.findViewById(R.id.button_change_ringtone_card);

                changeRingtone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        event.onEvent(null);
                    }
                });
            }
        }

    }

    public static class CardUpdateAllRingtones extends Card{
        jEvent event;

        public CardUpdateAllRingtones(Context context, jEvent event) {
            super(context);
            this.event = event;
            init();
        }

        private void init(){
            setTitle("Update ringtones");
            addCardExpand(new CardExpandUpdateAll(getContext(), event));
        }

        @Override
        public int getType() {
            return CARD_TYPE_UPDATE_RINGTONES;
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            super.setupInnerViewElements(parent, view);
            setViewToClickToExpand(ViewToClickToExpand.builder().setupView(parent));
        }

        //card expand class

        private class CardExpandUpdateAll extends CardExpand {
            jEvent event;

            public CardExpandUpdateAll(Context context, jEvent event) {
                super(context, R.layout.card_expand_update_ringtones);
                this.event = event;
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View view) {
                Button updateRingtones = (Button) view.findViewById(R.id.button_update_ringtones_card);
                updateRingtones.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        event.onEvent(null);
                    }
                });
            }
        }

    }

    public static class CardSettings extends Card{
        jEvent onChangeSyncContactEvent;
        jEvent getAllContactsEvent;
        CardSettingsExpand cardSettingsExpand;

        public CardSettings(Context context, jEvent onChangeSyncContact, jEvent getAllContacts) {
            super(context);
            onChangeSyncContactEvent = onChangeSyncContact;
            getAllContactsEvent = getAllContacts;
            init();
        }

        private void init(){
            setTitle("Settings");
            cardSettingsExpand = new CardSettingsExpand(getContext(),onChangeSyncContactEvent);
            addCardExpand(cardSettingsExpand);
            setOnExpandAnimatorEndListener(new OnExpandAnimatorEndListener() {
                @Override
                public void onExpandEnd(Card card) {
                    getAllContactsEvent.onEvent(null);
                }
            });
        }

        @Override
        public int getType() {
            return CARD_TYPE_SETTINGS;
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            super.setupInnerViewElements(parent, view);
            setViewToClickToExpand(ViewToClickToExpand.builder().setupView(parent));
        }

        public void setContactList(List<ContactForSettings> list){
            cardSettingsExpand.setListContacts(list);
        }

        private class CardSettingsExpand extends CardExpand{
            ShimmerTextView shimmerTextView;
            ListView listViewContacts;
            jEvent onChangeSyncContactEvent;
            private Shimmer animator;

            public CardSettingsExpand(Context context, jEvent onChangeSyncContact) {
                super(context, R.layout.card_expand_settings);
                onChangeSyncContactEvent = onChangeSyncContact;
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View view) {
                shimmerTextView = (ShimmerTextView) view.findViewById(R.id.shimmer_loading_card_expand_settings);
                listViewContacts = (ListView) view.findViewById(R.id.list_contacts_settings_card_setting_expand);

                Log.d("apkapk","View getContext: "+ view.getContext()+" Just getContext: "+getContext());
                animator = new Shimmer();
                animator.setDuration(3000);
                onExpanded();
            }

            private void onExpanded(){
                animator.start(shimmerTextView);
                //listViewContacts.setVisibility(View.GONE);
            }

            //methods for data upload

            public void setListContacts(final List<ContactForSettings> list){
                MainActivity.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listViewContacts.setAdapter(new SettingsContactsAdapter(getContext(), list, onChangeSyncContactEvent));

                        listViewContacts.setVisibility(View.VISIBLE);
                        shimmerTextView.setVisibility(View.GONE);
                    }
                });

            }
        }


    }
}

package com.example.bugdroid.lolquests;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robrua.orianna.type.core.game.Game;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by BugDroid on 04/12/2016.
 */

public class GameAdapter extends ArrayAdapter<Game> {
    private String TAG = "TESTE";

    public GameAdapter(Context context, List<Game> matches) {
        super(context, R.layout.match_layout, matches);
    }

    // Variaveis
    private int textColor;
    private double summonerGold;
    private Long kills, deaths, assists; // various stats
    private Long[] itemID = new Long[7]; // item id for each item
    private String[] itemURL = new String[7]; //image url for each item
    private Long cs; // minions farmados
    private String champIconURL; //image url for champion image
    private String[] summonerSpellKey = new String[2];
    private String[] summonerSpellURL = new String[2];
    private String matchHistoryURL;
    private String date;
    private String queueTypeText;
    String correctVers;
    private SimpleDateFormat dt = new SimpleDateFormat("MMMM d, yyyy");
    private Boolean win;

    private TextView championName; //text view for the summoner's played champ
    private TextView score;  //text view for the summoner's kda
    private TextView matchDate;
    private TextView csText;
    private TextView queueType;
    private TextView matchURI;
    private ImageView item0, item1, item2, item3, item4, item5, item6; //image views of each item slot
    private ImageView champImage; //image view for champion played by the summoner
    private ImageView summonerSpell1, summonerSpell2;
    private ImageView goldIcon;
    private ImageView csIcon;
    private TextView goldText;
    private View divider;
    private LinearLayout goldCs;
    private LinearLayout summonerSpellBox;

    private Game selectedGame; //the current match on the list
    private Game testGame;

    private Champion champ; //champion played by the main summoner

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());     //inflates layout
        View theView = inflater.inflate(R.layout.match_layout, parent, false);

        score = (TextView) theView.findViewById(R.id.scoreText); //gets reference to views
        championName = (TextView) theView.findViewById(R.id.championNameText);
        champImage = (ImageView) theView.findViewById(R.id.champImage);
        item0 = (ImageView) theView.findViewById(R.id.item0);
        item1 = (ImageView) theView.findViewById(R.id.item1);
        item2 = (ImageView) theView.findViewById(R.id.item2);
        item3 = (ImageView) theView.findViewById(R.id.item3);
        item4 = (ImageView) theView.findViewById(R.id.item4);
        item5 = (ImageView) theView.findViewById(R.id.item5);
        item6 = (ImageView) theView.findViewById(R.id.item6);
        summonerSpell1 = (ImageView) theView.findViewById(R.id.summonerSpell1);
        summonerSpell2 = (ImageView) theView.findViewById(R.id.summonerSpell2);
        csText = (TextView) theView.findViewById(R.id.csText);
        matchDate = (TextView) theView.findViewById(R.id.matchDate);
        queueType = (TextView) theView.findViewById(R.id.queueType);
        matchURI = (TextView) theView.findViewById(R.id.matchURI);
        summonerSpellBox = (LinearLayout) theView.findViewById(R.id.summonerSpellBox);

        selectedGame = getItem(position);
        //gets the match based on the index on the list

        testGame = getItem(0);

        Log.d(TAG, String.valueOf(testGame.getStats().getKills()));
        Log.d(TAG, String.valueOf(testGame.getStats().getDeaths()));
        Log.d(TAG, String.valueOf(testGame.getStats().getAssists()));
        Log.d(TAG, String.valueOf(testGame.getStats().getMinionsKilled()));
        Log.d(TAG, String.valueOf(testGame.getStats().getLevel()));

        divider = theView.findViewById(R.id.divider);
        win = selectedGame.getStats().getWin();
        champ = selectedGame.getChampion();
        kills = (long) selectedGame.getStats().getKills();
        deaths = (long) selectedGame.getStats().getDeaths();
        assists = (long) selectedGame.getStats().getAssists();
        summonerGold = (long) selectedGame.getStats().getGoldEarned();
        summonerGold = ((double) Math.round(summonerGold / 1000 * 10)) / 10;
        cs = (long) selectedGame.getStats().getMinionsKilled() + selectedGame.getStats().getNeutralMinionsKilledEnemyJungle() + selectedGame.getStats().getNeutralMinionsKilledYourJungle();
        queueTypeText = selectedGame.getSubType().name();

        try {
            itemID[0] = selectedGame.getStats().getItem0ID();
            itemID[1] = selectedGame.getStats().getItem1ID();
            itemID[2] = selectedGame.getStats().getItem2ID();
            itemID[3] = selectedGame.getStats().getItem3ID();
            itemID[4] = selectedGame.getStats().getItem4ID();
            itemID[5] = selectedGame.getStats().getItem5ID();
            itemID[6] = selectedGame.getStats().getItem6ID();
            summonerSpellKey[0] = selectedGame.getSummonerSpell1().getKey();
            summonerSpellKey[1] = selectedGame.getSummonerSpell2().getKey();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        itemURL[0] = "http://ddragon.leagueoflegends.com/cdn/6.23.1/img/item/" + itemID[0] + ".png"; //gets image urls
        itemURL[1] = "http://ddragon.leagueoflegends.com/cdn/6.23.1/img/item/" + itemID[1] + ".png";
        itemURL[2] = "http://ddragon.leagueoflegends.com/cdn/6.23.1/img/item/" + itemID[2] + ".png";
        itemURL[3] = "http://ddragon.leagueoflegends.com/cdn/6.23.1/img/item/" + itemID[3] + ".png";
        itemURL[4] = "http://ddragon.leagueoflegends.com/cdn/6.23.1/img/item/" + itemID[4] + ".png";
        itemURL[5] = "http://ddragon.leagueoflegends.com/cdn/6.23.1/img/item/" + itemID[5] + ".png";
        itemURL[6] = "http://ddragon.leagueoflegends.com/cdn/6.23.1/img/item/" + itemID[6] + ".png";
        summonerSpellURL[0] = "http://ddragon.leagueoflegends.com/cdn/6.23.1/img/spell/" + summonerSpellKey[0] + ".png ";
        summonerSpellURL[1] = "http://ddragon.leagueoflegends.com/cdn/6.23.1/img/spell/" + summonerSpellKey[1] + ".png";
        champIconURL = "http://ddragon.leagueoflegends.com/cdn/6.23.1/img/champion/" + champ.getKey() + ".png";

        for (int i = 0; i < 7; i++) //check for empty item slots
        {
            if (itemID[i] == 0) {
                itemURL[i] = "http://i.imgur.com/M3e1IqG.png"; //shows empty item slot
            }
        }

        Picasso.with(getContext()).load(itemURL[0]).into(item0); //loads image url's into image views
        Picasso.with(getContext()).load(itemURL[1]).into(item1);
        Picasso.with(getContext()).load(itemURL[2]).into(item2);
        Picasso.with(getContext()).load(itemURL[3]).into(item3);
        Picasso.with(getContext()).load(itemURL[4]).into(item4);
        Picasso.with(getContext()).load(itemURL[5]).into(item5);
        Picasso.with(getContext()).load(itemURL[6]).into(item6);
        Picasso.with(getContext()).load(summonerSpellURL[0]).into(summonerSpell1);
        Picasso.with(getContext()).load(summonerSpellURL[1]).into(summonerSpell2);
        Picasso.with(getContext()).load(champIconURL).into(champImage); //loads image into UI

        if (deaths != 0) {
            double kdaValue = ((double) kills + (double) assists) / (double) deaths;
            kdaValue = Math.round(kdaValue * 100.0) / 100.0;
            score.setText(String.valueOf(kills) + "/" + String.valueOf(deaths) + "/" + String.valueOf(assists) + " (" + String.valueOf(kdaValue) + ")");
        } else {
            score.setText(String.valueOf(kills) + "/" + String.valueOf(deaths) + "/" + String.valueOf(assists) + "(∞)");
        }

        if (win) { //changes color of text based on win/victory
            textColor = Color.parseColor("#4CAF50");
        } else {
            textColor = Color.parseColor("#B71C1C");
        }

        date = dt.format(selectedGame.getCreateDate());

        matchDate.setText(date);
        csText.setText(String.valueOf(cs));
        championName.setText(String.valueOf(champ.getName()));
        matchURI.setText(matchHistoryURL);
        csText.setTextColor(textColor);
        score.setTextColor(textColor);
        championName.setTextColor(textColor);

        return theView;
    }
}


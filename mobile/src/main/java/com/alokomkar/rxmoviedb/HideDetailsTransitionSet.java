package com.alokomkar.rxmoviedb;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.View;

import com.alokomkar.rxmoviedb.moviedetails.MovieDetailsLayout;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class HideDetailsTransitionSet extends TransitionSet {
    private static final String TITLE_TEXT_VIEW_TRANSITION_NAME = "titleTextView";
    private static final String CARD_VIEW_TRANSITION_NAME = "cardView";
    private final String transitionName;
    private final View from;
    private final MovieDetailsLayout to;
    private final Context context;

    public HideDetailsTransitionSet(final Context ctx, final String transitionName, final View from, final MovieDetailsLayout to) {
        context = ctx;
        this.transitionName = transitionName;
        this.from = from;
        this.to = to;
        addTransition(textResize());
        addTransition(shared());
    }

    private String titleTransitionName() {
        return transitionName + TITLE_TEXT_VIEW_TRANSITION_NAME;
    }

    private String cardViewTransitionName() {
        return transitionName + CARD_VIEW_TRANSITION_NAME;
    }

    private Transition textResize() {

        if(from!=null) {
            return new TransitionBuilder(new TextResizeTransition())
                    .link (to.title,from.findViewById(R.id.movieNameTextView), titleTransitionName())
                    .build();
        }
        else
            return  null;
    }


    private Transition shared() {
        if(from!=null) {
            return new TransitionBuilder(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
                    .link(to.imageViewPlaceDetails, from.findViewById(R.id.rootId), transitionName)
                    .link(to.mainContent,from,  cardViewTransitionName())
                    .build();
        }
        else
            return null;
    }
}
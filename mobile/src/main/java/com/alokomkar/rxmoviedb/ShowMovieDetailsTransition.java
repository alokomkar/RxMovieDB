package com.alokomkar.rxmoviedb;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.View;

import com.alokomkar.rxmoviedb.moviedetails.MovieDetailsLayout;

/**
 * Created by rahul on 15/06/17.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ShowMovieDetailsTransition extends TransitionSet {
    private static final String TITLE_TEXT_VIEW_TRANSITION_NAME = "titleTextView";
    private static final String CARD_VIEW_TRANSITION_NAME = "cardView";
    private final String transitionName;
    private final View from;
    private final MovieDetailsLayout to;
    private final Context context;

    public ShowMovieDetailsTransition(final Context ctx, final String transitionName, final View from, final MovieDetailsLayout to) {
        context = ctx;
        this.transitionName = transitionName;
        this.from = from;
        this.to = to;
        addTransition(textResize());
        addTransition(slide());
        addTransition(shared());
    }

    private String titleTransitionName() {
        return transitionName + TITLE_TEXT_VIEW_TRANSITION_NAME;
    }

    private String cardViewTransitionName() {
        return transitionName + CARD_VIEW_TRANSITION_NAME;
    }

    private Transition textResize() {
        return new TransitionBuilder(new TextResizeTransition())
                .link(from.findViewById(R.id.movieNameTextView), to.title, titleTransitionName())
                .build();
    }

    private Transition slide() {
        return new TransitionBuilder(TransitionInflater.from(context).inflateTransition(R.transition.movie_details_enter_transition))
                .excludeTarget(transitionName, true)
                .excludeTarget(to.title, true)
                .excludeTarget(to.mainContent, true)
                .build();
    }

    private Transition shared() {
        return new TransitionBuilder(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
                .link(from.findViewById(R.id.rootId), to.imageViewPlaceDetails, transitionName)
                .link(from, to.mainContent, cardViewTransitionName())
                .build();
    }
}

package com.alokomkar.rxmoviedb;

public class TransitionUtils {
    private static final String DEFAULT_TRANSITION_NAME = "transition";

    public static int getItemPositionFromTransition(final String transitionName,int recyclerviewId) {
        return Integer.parseInt(transitionName.replace(DEFAULT_TRANSITION_NAME+recyclerviewId,""));
    }

    public static String getRecyclerViewTransitionName(final int position,int recyclerviewId) {
        return DEFAULT_TRANSITION_NAME +recyclerviewId+position;
    }
}

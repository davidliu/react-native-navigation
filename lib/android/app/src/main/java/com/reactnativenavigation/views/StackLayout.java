package com.reactnativenavigation.views;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.reactnativenavigation.parse.Options;
import com.reactnativenavigation.presentation.OptionsPresenter;
import com.reactnativenavigation.utils.CompatUtils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class StackLayout extends RelativeLayout implements TitleBarButton.OnClickListener {

    private final TopBar topBar;
    private Fab fab;
    private FabMenu fabMenu;

    public StackLayout(Context context) {
        super(context);
        topBar = new TopBar(context, this);
        topBar.setId(CompatUtils.generateViewId());
        createLayout();
    }

    void createLayout() {
        addView(topBar, MATCH_PARENT, WRAP_CONTENT);
    }

    @Override
    public void onPress(String buttonId) {

    }

    public void applyOptions(Options options, ReactComponent component) {
        new OptionsPresenter(topBar, component).applyOptions(options);
        applyFabOptions(options, component);
    }

    private void applyFabOptions(Options options, ReactComponent component) {
        if (options.fabOptions.id.hasValue()) {
            if (fab == null) {
                fab = new Fab(getContext(), options.fabOptions);
                fab.setOnClickListener(v -> component.sendOnNavigationButtonPressed(options.fabOptions.id.get()));
                addView(fab);
            } else {
                fabMenu.bringToFront();
                fab.applyOptions(options.fabOptions);
            }
        }
        if (options.fabMenuOptions.id.hasValue()) {
            OnClickListener clickListener = v -> component.sendOnNavigationButtonPressed(options.fabMenuOptions.id.get());
            if (fabMenu == null) {
                fabMenu = new FabMenu(getContext(), options.fabMenuOptions, clickListener);
                addView(fabMenu);
            } else {
                fabMenu.bringToFront();
                fabMenu.applyOptions(options.fabMenuOptions, clickListener);
            }
        }
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    public TopBar getTopBar() {
        return topBar;
    }

    public void clearOptions() {
        topBar.clear();
    }

    public void setupTopTabsWithViewPager(ViewPager viewPager) {
        topBar.setupTopTabsWithViewPager(viewPager);
    }
}

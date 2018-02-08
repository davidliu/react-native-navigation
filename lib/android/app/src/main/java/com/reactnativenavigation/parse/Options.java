package com.reactnativenavigation.parse;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.reactnativenavigation.utils.TypefaceLoader;

import org.json.JSONObject;

public class Options implements DEFAULT_VALUES {

    public enum BooleanOptions {
        True,
        False,
        NoValue;

        static BooleanOptions parse(String value) {
            if (!TextUtils.isEmpty(value)) {
                return Boolean.valueOf(value) ? True : False;
            }
            return NoValue;
        }
    }

    @NonNull
    public static Options parse(TypefaceLoader typefaceManager, JSONObject json) {
        return parse(typefaceManager, json, new Options());
    }

    @NonNull
    public static Options parse(TypefaceLoader typefaceManager, JSONObject json, @NonNull Options defaultOptions) {
        Options result = new Options();
        if (json == null) return result;

        result.topBarOptions = TopBarOptions.parse(typefaceManager, json.optJSONObject("topBar"));
        result.topTabsOptions = TopTabsOptions.parse(json.optJSONObject("topTabs"));
        result.topTabOptions = TopTabOptions.parse(typefaceManager, json.optJSONObject("topTab"));
        result.bottomTabOptions = BottomTabOptions.parse(json.optJSONObject("bottomTab"));
        result.bottomTabsOptions = BottomTabsOptions.parse(json.optJSONObject("bottomTabs"));
        result.overlayOptions = OverlayOptions.parse(json.optJSONObject("overlay"));
        result.fabOptions = FabOptions.parse(json.optJSONObject("fab"));
        result.fabMenuOptions = FabMenuOptions.parse(json.optJSONObject("fabMenu"));

        return result.withDefaultOptions(defaultOptions);
    }

    @NonNull public TopBarOptions topBarOptions = new TopBarOptions();
    @NonNull public TopTabsOptions topTabsOptions = new TopTabsOptions();
    @NonNull public TopTabOptions topTabOptions = new TopTabOptions();
    @NonNull public BottomTabOptions bottomTabOptions = new BottomTabOptions();
    @NonNull public BottomTabsOptions bottomTabsOptions = new BottomTabsOptions();
    @NonNull public OverlayOptions overlayOptions = new OverlayOptions();
    @NonNull public FabOptions fabOptions = new FabOptions();
    @NonNull public FabMenuOptions fabMenuOptions = new FabMenuOptions();

    void setTopTabIndex(int i) {
        topTabOptions.tabIndex = i;
    }

    public void mergeWith(final Options other) {
        topBarOptions.mergeWith(other.topBarOptions);
        topTabsOptions.mergeWith(other.topTabsOptions);
        bottomTabOptions.mergeWith(other.bottomTabOptions);
        bottomTabsOptions.mergeWith(other.bottomTabsOptions);
        fabOptions.mergeWith(other.fabOptions);
        fabMenuOptions.mergeWith(other.fabMenuOptions);
    }

    Options withDefaultOptions(final Options other) {
        topBarOptions.mergeWithDefault(other.topBarOptions);
        topTabsOptions.mergeWithDefault(other.topTabsOptions);
        bottomTabOptions.mergeWithDefault(other.bottomTabOptions);
        bottomTabsOptions.mergeWithDefault(other.bottomTabsOptions);
        fabOptions.mergeWithDefault(other.fabOptions);
        fabMenuOptions.mergeWithDefault(other.fabMenuOptions);
        return this;
    }
}

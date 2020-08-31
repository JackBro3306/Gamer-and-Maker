package com.typedef.gam.di.module;

import android.app.Activity;

import androidx.fragment.app.Fragment;


import com.typedef.gam.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment){
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    Activity provideActivity() {
        return fragment.getActivity();
    }
}

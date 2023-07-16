package com.enesaksoy.sportsapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.enesaksoy.sportsapp.adapter.ResultAdapter
import com.enesaksoy.sportsapp.adapter.NewsAdapter
import com.enesaksoy.sportsapp.adapter.FixtureAdapter
import com.enesaksoy.sportsapp.adapter.TableAdapter
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SportsFragmentFactory @Inject constructor(
    private val adapter: NewsAdapter,
    private val adapter2: FixtureAdapter,
    private val tableAdapter: TableAdapter,
    private val glide : RequestManager,
    private val resultAdapter: ResultAdapter,
    private val auth: FirebaseAuth
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            NewsFragment::class.java.name -> NewsFragment(adapter)
            FixtureFragment::class.java.name -> FixtureFragment(resultAdapter,adapter2,auth)
            IntroFragment::class.java.name -> IntroFragment()
            NewsDetailsFragment::class.java.name -> NewsDetailsFragment(glide)
            SignUpFragment::class.java.name -> SignUpFragment(auth)
            TableFragment::class.java.name -> TableFragment(tableAdapter,auth)
            SignInFragment::class.java.name -> SignInFragment(auth)
            UserFragment::class.java.name -> UserFragment(auth)
            else -> super.instantiate(classLoader, className)
        }
    }
}
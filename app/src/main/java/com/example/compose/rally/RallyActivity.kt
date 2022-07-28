/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.rally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.compose.rally.data.UserData
import com.example.compose.rally.ui.accounts.AccountsBody
import com.example.compose.rally.ui.accounts.SingleAccountBody
import com.example.compose.rally.ui.bills.BillsBody

import com.example.compose.rally.ui.components.RallyTabRow
import com.example.compose.rally.ui.overview.OverviewBody
import com.example.compose.rally.ui.theme.RallyTheme

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */
class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
        // values() => Enum Class
        val allScreens = RallyScreen.values().toList()
//        var currentScreen by rememberSaveable { mutableStateOf(RallyScreen.Overview) }

        val navController = rememberNavController()
        // 현재 뒤로가기 스택의 마지막을 가져옴 (현재 바라보고 있는 화면을 가져옴)
        val backstackEntry = navController.currentBackStackEntryAsState()

        //현재 네비에기션스택의 라우트 이름으로 랠리 스크린 현재 이넘 가져오기
        // 예) Bills 마지막 값일때 fromRoute Helper 함수로  RallyScreen 을 가져와라
        val currentScreen = RallyScreen.fromRoute(
            backstackEntry.value?.destination?.route
        )

        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = allScreens,
                    onTabSelected = { screen ->
                        navController.navigate(screen.name)
//                        currentScreen = screen
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            RallyNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
        
        }
    }
}

// OnAccountCLick  등등 각각의 아이템을 클릭했을때 이 함수를 사용하여 넘겨지게 된다
 fun navigateToSingleAccount(
    navController: NavHostController,
    accountName: String
) {
    // 실제 값이 들어가는 곳   => /Account/{name}
    //Accounts/쩡대리
    navController.navigate("${RallyScreen.Accounts.name}/$accountName")
}
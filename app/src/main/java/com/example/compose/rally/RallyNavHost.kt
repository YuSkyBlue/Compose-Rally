package com.example.compose.rally

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.rally.data.UserData
import com.example.compose.rally.ui.accounts.AccountsBody
import com.example.compose.rally.ui.accounts.SingleAccountBody
import com.example.compose.rally.ui.bills.BillsBody
import com.example.compose.rally.ui.overview.OverviewBody

@Composable
fun RallyNavHost(
    navController : NavHostController,
    modifier: Modifier
){
    NavHost(
        navController = navController,
        startDestination = RallyScreen.Overview.name, // 처음시작 스크린
        modifier = modifier
    ) {
        val accountsName = RallyScreen.Accounts.name

        //Accounts/name
        composable(
            route = "$accountsName/{name}",
            arguments = listOf(
                navArgument("name") {
                    // Make argument type safe
                    type = NavType.StringType
                }
            ),
            deepLinks =  listOf(navDeepLink {
                uriPattern = "rally://$accountsName/{name}"
            })


            )
        {       // Account/{name} 이 들어왔을때 view만 그려주면 된다
                entry -> // 이름 키{name} 값을 찾아서  NavBackStackEntry argument를 찾아 본다
            val accountName = entry.arguments?.getString("name")
            // 찾은 첫번째 아이템을 매치 시킨다
            val account = UserData.getAccount(accountName)
            // SingleAccountBody 에  데이터를 넘긴다
            SingleAccountBody(account = account)
        }
        //OverView
        composable(RallyScreen.Overview.name) {
            OverviewBody(onClickSeeAllAccounts = { navController.navigate(RallyScreen.Accounts.name) },
                onClickSeeAllBills = { navController.navigate(RallyScreen.Bills.name)},
                onAccountClick = { selectedAccount ->
                    navigateToSingleAccount(navController, selectedAccount)
                    //  onAccountClick (이벤트 감지)-> composable로 들어온 아이템 리스트 키값으로 찾고 -> SingleAccountBody() 에 그려준다
                }
            )
        }
        //Accounts
        composable(RallyScreen.Accounts.name) {
            AccountsBody(accounts = UserData.accounts,
                onAccountClick = { selectedAccount ->
                    navigateToSingleAccount(navController, selectedAccount)
                })
        }
        //Bills
        composable(RallyScreen.Bills.name) {
            BillsBody(bills = UserData.bills)
        }
    }

}
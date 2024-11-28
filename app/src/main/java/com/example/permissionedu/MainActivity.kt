package com.example.permissionedu

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.permissionedu.ui.theme.PermissionEduTheme


class MainActivity : ComponentActivity() {

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(isGranted){
            //권한이 허용된 경우에 실행 될 코드
            Toast.makeText(applicationContext, "알림호출", Toast.LENGTH_SHORT).show()
        }else {
            //권한이 거부된 경우에 실행 될 코드

            //사용자가 허용안함을 클릭한 경우 true 반환
            //사용자가 허용안함을 클릭하고 다시 묻는 창에서도 취소를 클릭했을 때 false를 반환
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)){
                AlertDialog.Builder(this)
                    .setTitle("권한 요청")
                    .setMessage("푸시알림 권한이 필요합니다.")
                    .setPositiveButton("확인") { _, _ ->
                        permissionRequest()
                    }
                    .setNegativeButton("취소") { _, _ ->
                        // Dialog에서 취소 버튼을 누른 경우에 실행할 코드
                        Toast.makeText(applicationContext, "사용 불가", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }else {
                Toast.makeText(applicationContext, "설정에서 권한을 허용해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PermissionEduTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   Column (
                       horizontalAlignment = Alignment.CenterHorizontally,
                       verticalArrangement = Arrangement.Center,
                       modifier = Modifier
                           .fillMaxSize()
                           .padding(innerPadding)
                   ){
                       Button(
                           onClick = {
                               permissionRequest()
                           }
                       ) {
                           Text(
                               text = "알림호출"
                           )
                       }
                   }
                }
            }
        }
    }

    private fun permissionRequest(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                //권한이 허용되지 않은 경우
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }else {
                //권한이 혀용 된 경우
                Toast.makeText(applicationContext, "알림호출", Toast.LENGTH_SHORT).show()
            }
        }else {
            //권한이 필요하지 않은 경우(바로 기능 실행)
            Toast.makeText(applicationContext, "알림호출", Toast.LENGTH_SHORT).show()
        }
    }
}


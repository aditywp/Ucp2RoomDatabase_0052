package com.example.ucp2.ui.view.dokter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.R
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.ui.viewmodel.HomeScreenViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel

@Composable
fun HomeDokterView(
    viewModel: HomeScreenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDokter: () -> Unit = {},
    onAddJadwal: () -> Unit = {},
    onDetailJadwal: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopHeader()
        },
    ) { padding ->
        val homeUiState by viewModel.homeUIState.collectAsState()
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            SchedulesSection(onDetailJW = onDetailJadwal, onAddJadwal = onAddJadwal, onAddDokter = onAddDokter)
            TopDoctorsSection(
                listDokter = homeUiState.listDokter,
                onCLick = {
                    println(it)
                },
                modifier = Modifier.padding(top = 10.dp),
                onAddDokter = onAddDokter
            )
        }
    }
}


@Composable
fun TopHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp))
            .background(Color(0xFF344e41))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(66.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "CIPTA SEHAT",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFdad7cd))
                    .border(3.dp, Color.White, CircleShape)
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 160.dp)
    )
    {
        SearchBar()
    }
}


@Composable
fun SearchBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth().offset(x = 72.dp, y = 10.dp), verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Brush.horizontalGradient(listOf(Color(0xFFE9E3F3), Color(0xFFE9E3F3))))
                    .shadow(5.dp, RoundedCornerShape(25.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp, color = Color.Gray),
                    decorationBox = { innerTextField ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                modifier = Modifier.size(24.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box { innerTextField() }
                        }
                    }
                )
            }
        }
    }

}


@Composable
fun SchedulesSection(
    onDetailJW: () -> Unit
    , onAddJadwal: () -> Unit,
    onAddDokter: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .size(130.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onAddDokter,
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF588157),
                ),
                contentPadding = PaddingValues(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.docter),
                    contentDescription = "Add dokter",
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Tambah Dokter", textAlign = TextAlign.Center)
        }

        Column(
            modifier = Modifier
                .size(130.dp), // Define size for each column to keep consistent spacing
            horizontalAlignment = Alignment.CenterHorizontally // Center the content horizontally
        ) {
            Button(
                onClick = onAddJadwal,
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF588157),
                ),
                contentPadding = PaddingValues(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.jadwal1),
                    contentDescription = "Add Jadwal",
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp)) // Space between the button and the text
            Text("Tambah Jadwal", textAlign = TextAlign.Center) // Text below the button
        }

        // Third Column for "Melihat Jadwal"
        Column(
            modifier = Modifier
                .size(130.dp), // Define size for each column to keep consistent spacing
            horizontalAlignment = Alignment.CenterHorizontally // Center the content horizontally
        ) {
            Button(
                onClick = onDetailJW,
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF588157),
                ),
                contentPadding = PaddingValues(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.jadwal2),
                    contentDescription = "View Schedule",
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Lihat Jadwal", textAlign = TextAlign.Center)
        }
    }
}



@Composable
fun TopDoctorsSection(
    listDokter: List<Dokter>,
    onCLick: (String) -> Unit = { },
    modifier: Modifier = Modifier,
    onAddDokter: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "LIST DOCTORS",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp),
        )


        LazyColumn {
            items(items = listDokter, itemContent = { dokter ->
                val spesialisasiColor = when (dokter.spesialis) {
                    "Umum" -> Color(0xFFE91E63)
                    "Anak" -> Color(0xFF741EBE)
                    "Kandungan" -> Color(0xFF0E7ED3)
                    "Gigi" -> Color(0xFF0FBD03)
                    "Bedah" -> Color(0xFFBA68C8)
                    else -> Color.Gray
                }

                DoctorCard(
                    name = dokter.namaDokter,
                    speciality = dokter.spesialis,
                    location = dokter.klinik,
                    workHours = dokter.jamKerja,
                    onClick = { onCLick(dokter.idDokter) },
                    color = spesialisasiColor
                )
            })
        }
    }
}


@Composable
fun DoctorCard(
    name: String,
    speciality: String,
    location: String,
    workHours: String,
    onClick: () -> Unit,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable(onClick = onClick)
            .background(Color.White)
            .border(
                width = 1.dp,
                brush = Brush.radialGradient(
                    colors = listOf(Color.Black, Color.Gray),
                    center = Offset(50f, 50f),
                    radius = 100f
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .offset(x = 18.dp, y = 1.dp)

    ) {
        Image(
            imageVector = Icons.Filled.Person,
            contentDescription = name,
            modifier = Modifier
                .padding(top = 5.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(color = color)
        )
        Spacer(modifier = Modifier.width(25.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(3.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = speciality,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    color = color
                )
            )
            Text(
                text = "Jam Kerja: $workHours",
                style = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.Black)
            )
            Text(
                text = "Klinik: $location",
                style = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.Black)
            )
        }
    }
}

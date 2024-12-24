package com.example.ucp2.ui.view.jadwal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.HomeJadwalUiState
import com.example.ucp2.ui.viewmodel.HomeJadwalViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeJadwalView(
    viewModel: HomeJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBackClick: () -> Unit,
    onAddJadwal: () -> Unit = {},
    onDetailJadwal: (String) -> Unit = {},
    onEditJadwal: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val homeUiState by viewModel.homeJadwalUiState.collectAsState()
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                onBack = onBackClick,
                judul = "Halaman Jadwal"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddJadwal,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(15.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Jadwal",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        BodyHomeJadwalView(
            homeJadwalUiState = homeUiState,
            onEditJadwal = { onEditJadwal(it) },
            onClick = onDetailJadwal,
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}

@Composable
fun BodyHomeJadwalView(
    homeJadwalUiState: HomeJadwalUiState,
    onEditJadwal: (String) -> Unit = {},
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeJadwalViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    var selectedJadwal: Jadwal? by remember { mutableStateOf(null) }

    when {
        homeJadwalUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        homeJadwalUiState.isError -> {
            LaunchedEffect(homeJadwalUiState.errorMessage) {
                homeJadwalUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }
        homeJadwalUiState.listJadwal.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada jadwal",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(homeJadwalUiState.listJadwal) { jadwal ->
                    JadwalCard(
                        jadwal = jadwal,
                        onClick = { onClick(jadwal.idJadwal) },
                        onDelete = {
                            selectedJadwal = jadwal
                            deleteConfirmationRequired = true
                        },
                        onEditJadwal = { onEditJadwal(jadwal.idJadwal) }
                    )
                }
            }
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                selectedJadwal?.let { jadwal ->
                    coroutineScope.launch {
                        viewModel.deleteJadwal(jadwal)
                        snackbarHostState.showSnackbar("Jadwal berhasil dihapus")
                    }
                }
            },
            onDeleteCancel = {
                deleteConfirmationRequired = false
            }
        )
    }
}

@Composable
fun JadwalCard(
    jadwal: Jadwal,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onEditJadwal: (String) -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Nama Dokter: ${jadwal.namaDokter}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Nama Pasien: ${jadwal.namaPasien}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tanggal Konsul: ${jadwal.tanggalKonsultasi}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Nomer Telp: ${jadwal.noHp}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Status: ${jadwal.status}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Tombol Edit dan Delete berada di bawah, sejajar
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Menyebarkan tombol sejajar
            ) {
                // Tombol Edit
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = { onEditJadwal(jadwal.idJadwal) },
                        modifier = Modifier
                            .size(36.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                            .background(Color.Blue)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "Edit Jadwal",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Edit",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }

                // Tombol Delete
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier
                            .size(36.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                            .background(Color.Red)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus Jadwal",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Hapus",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDeleteCancel,
        title = { Text(text = "Konfirmasi Hapus", fontWeight = FontWeight.Bold) },
        text = { Text(text = "Apakah Anda yakin ingin menghapus jadwal ini?") },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Hapus", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal")
            }
        },
        containerColor = Color.White,
        iconContentColor = Color.Black
    )
}

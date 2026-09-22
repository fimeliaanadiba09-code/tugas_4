<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulir Tugas HTML & JavaScript</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 20px;
            padding: 20px;
        }
        .container {
            max-width: 600px;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        h2 { margin-top: 0; }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="email"], input[type="number"], select {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .error {
            color: red;
            font-size: 12px;
            margin-top: 3px;
        }
        .options-container {
            margin-top: 5px;
        }
        .options-container label {
            font-weight: normal;
            display: inline-block;
            margin-right: 15px;
        }
        button {
            background-color: #007BFF;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
        #outputSection {
            background: #e9ecef;
            padding: 15px;
            border-radius: 4px;
            white-space: pre-wrap;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Formulir Input Data</h2>
        <form id="myForm" onsubmit="handleSubmit(event)">
            <!-- Input Teks -->
            <div class="form-group">
                <label for="nama">Input Teks (Nama Lengkap):</label>
                <input type="text" id="nama" required placeholder="Masukkan nama...">
            </div>

            <!-- Input Jumlah Pilihan Number dengan Error Handling (Spinner) -->
            <div class="form-group">
                <label for="jumlah">Input Jumlah (1 - 100):</label>
                <input type="number" id="jumlah" min="1" max="100" value="1">
                <div id="errorNumber" class="error"></div>
            </div>

            <!-- Input Email dengan Error Cek Pattern Matching (alert & minta masukan ulang) -->
            <div class="form-group">
                <label for="email">Input Email:</label>
                <input type="email" id="email" placeholder="contoh@domain.com">
            </div>

            <!-- Loop untuk membuat Check-box dan Radio-button dari Array -->
            <div class="form-group">
                <label>Pilihan Check-box (dari Array):</label>
                <div id="checkboxGroup" class="options-container"></div>
            </div>

            <div class="form-group">
                <label>Pilihan Radio-button (dari Array):</label>
                <div id="radioGroup" class="options-container"></div>
            </div>

            <!-- Tombol (Event Controller) -->
            <button type="submit" id="btnSubmit">Kirim Data</button>
        </form>
    </div>

    <!-- Output (JavaScript DOM) -->
    <div class="container">
        <h2>Output DOM</h2>
        <div id="outputSection">Belum ada data yang dikirim.</div>
    </div>

    <script>
        // Array untuk menyimpan teks pada opsi pilihan check-box dan radio-button
        const checkboxOptions = ['HTML', 'CSS', 'JavaScript', 'PHP'];
        const radioOptions = ['Pemula', 'Menengah', 'Mahir'];

        // Fungsi inisialisasi / loop untuk generate checkbox dan radio button ke DOM
        function renderDynamicOptions() {
            // Render Checkbox
            const cbContainer = document.getElementById('checkboxGroup');
            cbContainer.innerHTML = ''; // reset
            checkboxOptions.forEach((item, index) => {
                const id = `cb_${index}`;
                cbContainer.innerHTML += `
                    <label for="${id}">
                        <input type="checkbox" id="${id}" name="skills" value="${item}"> ${item}
                    </label>
                `;
            });

            // Render Radio Button
            const rdContainer = document.getElementById('radioGroup');
            rdContainer.innerHTML = ''; // reset
            radioOptions.forEach((item, index) => {
                const id = `rd_${index}`;
                const checked = index === 0 ? 'checked' : '';
                rdContainer.innerHTML += `
                    <label for="${id}">
                        <input type="radio" id="${id}" name="level" value="${item}" ${checked}> ${item}
                    </label>
                `;
            });
        }

        // Jalankan render saat halaman dimuat
        renderDynamicOptions();

        // Fungsi Validasi & Event Controller Submit
        function handleSubmit(event) {
            event.preventDefault(); // Mencegah reload halaman bawaan form

            // Ambil elemen
            const inputNama = document.getElementById('nama').value;
            const inputJumlahElem = document.getElementById('jumlah');
            const inputJumlah = parseInt(inputJumlahElem.value, 10);
            const errorNumberElem = document.getElementById('errorNumber');
            const inputEmailElem = document.getElementById('email');
            const inputEmail = inputEmailElem.value.trim();

            // Error handling number spinner manual
            errorNumberElem.textContent = '';
            if (isNaN(inputJumlah) || inputJumlah < 1 || inputJumlah > 100) {
                errorNumberElem.textContent = 'Error: Masukkan angka antara 1 sampai 100!';
                inputJumlahElem.focus();
                return;
            }

            // Error handling & pattern matching email manual + alert & minta masukan ulang
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailPattern.test(inputEmail)) {
                alert('Format email salah! Mohon masukkan ulang email yang valid.');
                inputEmailElem.focus();
                inputEmailElem.value = '';
                return;
            }

            // Ambil nilai checkbox yang dicentang
            const selectedCheckboxes = [];
            document.querySelectorAll('input[name="skills"]:checked').forEach((cb) => {
                selectedCheckboxes.push(cb.value);
            });

            // Ambil nilai radio yang dipilih
            const selectedRadio = document.querySelector('input[name="level"]:checked');
            const levelVal = selectedRadio ? selectedRadio.value : '-';

            // Output DOM
            const outputSection = document.getElementById('outputSection');
            outputSection.innerHTML = `
<strong>Hasil Output Data:</strong>
- Nama             : ${escapeHtml(inputNama)}
- Jumlah           : ${inputJumlah}
- Email            : ${escapeHtml(inputEmail)}
- Skills (Checkbox): ${selectedCheckboxes.length > 0 ? selectedCheckboxes.join(', ') : 'Tidak ada'}
- Level (Radio)    : ${levelVal}
            `.trim();
        }

        // Helper kecil untuk keamanan XSS dasar
        function escapeHtml(text) {
            return text.replace(/[&<>"']/g, function(m) {
                return { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;' }[m];
            });
        }
    </script>
</body>
</html>

# Reflection 1
Pada modul ini, saya telah mengimplementasikan dua fitur baru pada aplikasi berbasis Spring Boot, yaitu Edit Product dan Delete Product. Setelah melakukan pengecekan ulang terhadap source code yang telah dibuat, saya melakukan evaluasi terhadap penerapan clean code principles dan secure coding practices yang telah dipelajari.
### Clean Code Principles
Beberapa prinsip clean code yang telah diterapkan dalam source code saya antara lain:
1. Separation of Concerns:
Aplikasi dibagi ke dalam beberapa layer, yaitu Controller, Service, dan Repository.
Controller hanya bertanggung jawab untuk menangani request dan response.
Service menangani business logic.
Repository bertugas untuk mengelola data.
Pemisahan ini membuat kode lebih terstruktur, mudah dipahami, dan mudah dirawat.
2. Penamaan Variabel dan Method yang Jelas:
Nama class, method, dan variabel seperti editProductPage, deleteById, dan findById sudah menggambarkan fungsinya masing-masing. Hal ini membantu meningkatkan keterbacaan kode.
3. Reusable Code:
Method seperti findById digunakan kembali pada fitur edit dan delete, sehingga mengurangi duplikasi kode dan membuat program lebih efisien.

### Secure Coding Practices
Beberapa praktik secure coding yang telah diterapkan adalah:
1. Validasi Data melalui Path Variable:
Pengambilan data produk dilakukan berdasarkan productId, sehingga hanya produk dengan ID tertentu yang dapat diubah atau dihapus.
2. Konfirmasi Aksi Delete:
Pada fitur delete, terdapat konfirmasi di sisi client untuk mencegah penghapusan data secara tidak sengaja.
3. Tidak Mengakses Repository Secara Langsung dari Controller:
Controller hanya berkomunikasi dengan Service, sehingga akses data lebih terkontrol dan aman.

### Evaluasi dan Perbaikan Kode
Meskipun fitur telah berjalan dengan baik, terdapat beberapa hal yang masih dapat diperbaiki, antara lain:
1. Validasi Input:
Saat ini, belum terdapat validasi input (misalnya menggunakan anotasi @Valid). Ke depannya, validasi dapat ditambahkan untuk mencegah data tidak valid masuk ke sistem.
2. Error Handling:
Apabila productId tidak ditemukan, aplikasi belum menangani error secara eksplisit. Perbaikan dapat dilakukan dengan menambahkan pengecekan null dan menampilkan halaman error yang sesuai.
3. Keamanan Endpoint Delete:
Endpoint delete masih menggunakan metode GET. Untuk meningkatkan keamanan dan mengikuti best practice REST, sebaiknya fitur delete menggunakan metode POST atau DELETE.


# Refleksi 2

### 1. Reflection on Unit Testing and Code Coverage

Setelah menulis unit test, perasaan saya campur aduk. Di satu sisi, menulis unit test cukup membantu untuk memastikan bahwa fitur yang dibuat berjalan sesuai harapan. Tapi di sisi lain, jujur saja, menulis unit test itu cukup melelahkan karena harus memikirkan berbagai skenario, termasuk kasus normal (positive case) dan kasus error (negative case).

Menurut saya, tidak ada jumlah pasti berapa unit test yang harus dibuat dalam satu class. Idealnya, setiap *public method* memiliki beberapa unit test yang mencakup:
- Skenario normal (input valid)
- Skenario edge case
- Skenario error atau input tidak valid

Untuk memastikan unit test yang kita buat sudah cukup, kita bisa menggunakan **code coverage**. Code coverage adalah metrik yang menunjukkan seberapa banyak baris atau cabang kode kita yang dieksekusi saat unit test dijalankan. Dengan melihat code coverage, kita bisa tahu bagian mana dari kode yang belum pernah dites.

Namun, **100% code coverage tidak berarti kode kita bebas dari bug**. Code coverage hanya menunjukkan bahwa baris kode tersebut dijalankan, bukan bahwa logikanya sudah benar sepenuhnya. Masih mungkin ada bug pada kondisi tertentu, kesalahan logika, atau skenario yang tidak terpikirkan meskipun coverage-nya tinggi. Jadi, code coverage sebaiknya digunakan sebagai alat bantu, bukan sebagai satu-satunya penentu kualitas program.

---

### 2. Reflection on Functional Test Code Cleanliness

Jika setelah menulis `CreateProductFunctionalTest.java` saya diminta membuat functional test baru untuk mengecek jumlah produk di product list, lalu saya menyalin struktur test sebelumnya (setup WebDriver, inisialisasi variabel, dan konfigurasi Spring Boot), maka dari sisi *clean code* hal ini bisa menimbulkan beberapa masalah.

Masalah utama yang muncul adalah **duplikasi kode**. Setup seperti inisialisasi WebDriver, konfigurasi port, dan lifecycle (`@BeforeEach` dan `@AfterEach`) akan ditulis ulang di banyak class. Ini membuat kode:
- Sulit dirawat (maintainability rendah)
- Rentan error jika ada perubahan konfigurasi
- Terlihat berantakan karena banyak bagian yang sebenarnya sama

Duplikasi kode seperti ini dapat menurunkan kualitas kode karena melanggar prinsip **DRY (Don't Repeat Yourself)**.

Untuk memperbaiki kebersihan kode, beberapa solusi yang bisa dilakukan antara lain:
- Membuat **base class** untuk functional test yang berisi setup dan teardown WebDriver, lalu test class lain melakukan `extends`
- Menggunakan **utility class** untuk fungsi-fungsi umum seperti membuka halaman atau menunggu elemen muncul
- Menyeragamkan cara pencarian elemen (misalnya selalu menggunakan `By.id` atau `By.name`)

Dengan cara ini, kode functional test menjadi lebih rapi, mudah dibaca, dan lebih mudah dikembangkan jika di masa depan jumlah test bertambah.

---

# Refleksi 3

## 1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.

Selama pengerjaan latihan ini, saya mengidentifikasi dan memperbaiki beberapa masalah kualitas kode yang ditemukan oleh SonarCloud untuk meningkatkan aksesibilitas dan reliabilitas aplikasi:

### 1. Atribut lang yang hilang pada tag <html>:

Saya menambahkan atribut lang="en" pada tag <html> di template Thymeleaf. Hal ini penting agar screen reader dan mesin pencari dapat mengenali bahasa utama halaman web, yang merupakan aspek fundamental dalam aksesibilitas web.

### 2. Asosiasi Label dan Kontrol pada Form:

Saya menyambungkan elemen label dengan kontrol input yang terkait menggunakan atribut for. Karena th:field di Thymeleaf secara otomatis menghasilkan atribut id, saya memastikan atribut for pada label memiliki nilai yang sama dengan ID tersebut (atau mendefinisikan ID secara eksplisit untuk membantu validasi IDE). Ini memungkinkan kursor fokus ke input saat label diklik dan membantu teknologi asistif menjelaskan fungsi form dengan benar.

## 2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

Menurut saya, implementasi alur kerja saat ini sudah memenuhi definisi Continuous Integration (CI) dan Continuous Deployment (CD). Untuk bagian Continuous Integration, setiap kali ada kode yang di-push atau pembuatan Pull Request, GitHub Actions secara otomatis menjalankan test suite dan analisis kualitas kode melalui SonarCloud untuk memastikan tidak ada kode yang rusak atau pola kualitas buruk yang masuk ke branch utama. Terkait Continuous Deployment, alur kerja telah dikonfigurasi untuk memicu redeploy otomatis ke Koyeb (PaaS) menggunakan Koyeb CLI segera setelah perubahan berhasil di-merge ke branch main. Otomatisasi ini menghilangkan intervensi manual dalam proses pengiriman perangkat lunak, sehingga versi aplikasi yang paling stabil dan terbaru selalu tersedia bagi pengguna secara instan.

URL Deployment: spicy-murial-module-1-codingstandards-josiah-a1bdcd2c.koyeb.app/
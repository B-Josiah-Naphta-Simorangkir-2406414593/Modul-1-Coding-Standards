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

# Reflection Module 3

## 1. Explain what principles you apply to your project!
Proyek ini menerapkan kelima prinsip SOLID untuk memastikan kode yang dihasilkan bersih, modular, dan mudah dikelola.

S - Single Responsibility Principle (SRP): Setiap kelas memiliki satu tanggung jawab utama. Contohnya, kelas ProductController hanya bertanggung jawab untuk menangani permintaan HTTP yang terkait dengan produk (membuat, menampilkan, mengedit, menghapus). Tanggung jawab untuk entitas lain, seperti Car, ditangani oleh kelasnya sendiri (CarController), sehingga setiap kelas fokus pada satu fungsi.

O - Open/Closed Principle (OCP): Perangkat lunak terbuka untuk ekstensi tetapi tertutup untuk modifikasi. Pada proyek ini, jika kita ingin menambahkan fungsionalitas baru seperti entitas "Elektronik", kita tidak perlu mengubah kode ProductController atau ProductService. Sebaliknya, kita akan membuat ElectronicController dan ElectronicService baru. Ini memungkinkan penambahan fitur baru tanpa merusak fungsionalitas yang sudah ada.

L - Liskov Substitution Principle (LSP): Objek dari subclass harus dapat menggantikan objek dari superclass tanpa mengganggu fungsionalitas program. Sebelumnya, prinsip ini dilanggar ketika CarController mewarisi ProductController. Ini adalah hubungan warisan yang salah karena CarController bukanlah pengganti yang valid untuk ProductController. Kode telah diperbaiki dengan memisahkan kedua kelas tersebut, sehingga tidak ada lagi pewarisan yang keliru dan LSP terpenuhi.

I - Interface Segregation Principle (ISP): Klien tidak boleh dipaksa untuk bergantung pada interface yang tidak mereka gunakan. Proyek ini menerapkan ISP dengan memiliki interface yang spesifik seperti ProductService dan CarService. Dengan demikian, ProductController hanya perlu tahu tentang metode-metode di ProductService dan tidak dipaksa untuk bergantung pada metode-metode untuk Car yang tidak relevan baginya.

D - Dependency Inversion Principle (DIP): Modul tingkat tinggi tidak bergantung pada modul tingkat rendah, melainkan pada abstraksi. Dalam proyek ini, ProductController (modul tingkat tinggi) tidak bergantung pada ProductServiceImpl (implementasi konkret), tetapi pada interface ProductService (abstraksi). Ini memungkinkan implementasi diubah (misalnya, dari penyimpanan di memori ke database) tanpa perlu mengubah kode di ProductController.

## 2. Explain the advantages of applying SOLID principles to your project with examples.

Menerapkan SOLID memberikan banyak keuntungan yang membuat proyek lebih mudah dikembangkan dan dipelihara dalam jangka panjang.

a. Kode Lebih Mudah Dipelihara (Maintainability): Ketika setiap kelas memiliki satu tanggung jawab (SRP), menemukan dan memperbaiki bug menjadi lebih cepat. Contoh: Jika ada bug pada halaman daftar produk, kita tahu pasti harus memeriksa ProductController dan ProductService, tanpa perlu membaca kode yang berhubungan dengan mobil.

b. Fleksibilitas dan Skalabilitas: Kode menjadi lebih mudah untuk dikembangkan. Contoh: Berkat OCP dan DIP, jika kita ingin mengganti penyimpanan produk dari ArrayList di memori ke database PostgreSQL, kita hanya perlu membuat kelas ProductServiceDbImpl yang mengimplementasikan ProductService. Setelah itu, kita cukup mengubah konfigurasi untuk menyuntikkan implementasi baru ini. ProductController tidak perlu diubah sama sekali.

c. Memudahkan Pengujian (Testability): Kode yang loosely coupled (tidak terikat erat) lebih mudah diuji secara terpisah. Contoh: Karena ProductController bergantung pada interface ProductService (DIP), kita dapat dengan mudah membuat mock object dari ProductService saat menguji ProductController. Ini memungkinkan kita untuk mengisolasi pengujian hanya pada logika controller tanpa bergantung pada lapisan layanan yang sebenarnya, membuat tes lebih cepat dan andal.

## 3. Explain the disadvantages of not applying SOLID principles to your project with examples.

Mengabaikan prinsip SOLID akan menghasilkan kode yang kaku, rapuh, dan sulit dikelola seiring waktu.

a. Kode Menjadi Kaku dan Rapuh (Rigid and Fragile): Perubahan kecil di satu bagian dapat menyebabkan kerusakan tak terduga di bagian lain. Contoh: Sebelum perbaikan, ProductController.java berisi definisi CarController. Mengubah sesuatu di ProductController bisa saja secara tidak sengaja merusak fungsionalitas CarController. Ini adalah contoh kode yang rapuh.

b. Sulit untuk Diuji: Kode yang terikat erat (tightly coupled) sulit diuji secara terisolasi. Contoh: Masalah awal pada ProductControllerTest adalah contoh nyata dari ini. Karena ProductController dan CarController berada dalam satu file dan melanggar beberapa prinsip, framework pengujian menjadi bingung dan gagal menyiapkan tes dengan benar. Ketergantungan langsung pada CarServiceImpl (pelanggaran DIP) juga membuat mocking menjadi lebih sulit.

c. Sulit Dipahami dan Dikembangkan: Kode menjadi tidak intuitif dan membingungkan bagi pengembang lain atau bahkan diri sendiri di masa depan. Contoh: Seorang pengembang baru yang melihat logika CarController di dalam file bernama ProductController.java (pelanggaran SRP) akan bingung. Ini meningkatkan waktu yang dibutuhkan untuk memahami alur program dan membuat perubahan, sehingga memperlambat proses pengembangan.

# Reflection Module 4

## 1. TDD Flow Reflection

Berdasarkan prinsip dari Percival (2017), alur Test-Driven Development (TDD) yang saya terapkan dalam pengerjaan tutorial ini sangat berguna.

Manfaat yang dirasakan: Dengan menulis unit test terlebih dahulu (seperti pada OrderTest dan OrderRepositoryTest), saya dipaksa untuk memikirkan requirement dan edge cases secara detail sebelum menulis implementasi. Contohnya, tes testCreateOrderInvalidStatus memastikan bahwa sistem tidak menerima status sembarangan sejak awal perancangan.

Keamanan Kode: TDD memberikan rasa aman saat saya nantinya mengisi body dari method yang masih return null tadi. Jika saya membuat kesalahan logika, tes yang sudah ada akan langsung mendeteksi (red), sehingga saya bisa langsung memperbaikinya hingga menjadi green.

Rencana Berikutnya: Jika nanti tes terasa terlalu lambat atau sulit dibuat, saya perlu lebih memperhatikan test isolation. Penggunaan Mockito (seperti di OrderServiceTest) sudah sangat membantu, namun saya harus memastikan tidak terlalu banyak melakukan over-mocking agar tes tetap merepresentasikan perilaku sistem yang sebenarnya.

## 2. F.I.R.S.T. Principle Reflection

Setelah meninjau unit test yang telah dibuat, berikut adalah refleksi terhadap prinsip F.I.R.S.T:

Fast: Tes berjalan sangat cepat karena tidak ada keterkaitan dengan database eksternal atau jaringan. Penggunaan ArrayList di repository dan Mockito di service menjaga performa tes tetap optimal.

Independent: Setiap tes bersifat independen. Penggunaan @BeforeEach untuk melakukan setup data baru (seperti menginisialisasi orders dan products) memastikan bahwa status dari satu tes tidak mempengaruhi tes lainnya.

Repeatable: Tes ini dapat dijalankan di lingkungan mana pun (laptop saya maupun CI/CD) dengan hasil yang konsisten karena tidak bergantung pada variabel lingkungan yang dinamis.

Self-Validating: Semua tes menggunakan assertions (seperti assertEquals, assertNull, assertThrows) yang secara otomatis menentukan apakah tes berhasil atau gagal tanpa perlu pengecekan log manual.

Timely: Tes dibuat tepat waktu (sebelum implementasi), sesuai dengan kaidah TDD.
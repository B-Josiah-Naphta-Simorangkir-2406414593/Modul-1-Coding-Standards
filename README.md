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
package Tugas2;

import java.util.ArrayList;
import java.util.Scanner;

class Menu{
    private String nama;
    private double harga;
    private String kategori;

    public Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public double getHarga(){
        return harga;
    }
    public void setHarga(double harga) {
        this.harga = harga;
    }
    public String getKategori() {
        return kategori;
    }
    public void setKategori(String kategori){
        this.kategori = kategori;
    }

    @Override
    public String toString(){
        return nama + "- Rp." + harga + " (" +kategori+ ") ";
    }
}

public class Tugas2 {
    private static ArrayList<Menu> menus = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main (String[] agrs) {
        menus.add(new Menu("Ayam Goreng", 18000, "makanan"));
        menus.add(new Menu("Lele Goreng", 13000, "makanan"));
        menus.add(new Menu("Nila Goreng", 20000, "makanan"));
        menus.add(new Menu("Ati Goreng", 10000, "makanan"));
        menus.add(new Menu("Telur Goreng", 10000, "makanan"));
        menus.add(new Menu("Puyuh Goreng", 15000, "makanan"));
        menus.add(new Menu("Kubis Goreng", 6000, "makanan"));
        menus.add(new Menu ("Terong Goreng", 6000, "makanan"));
        menus.add(new Menu("Nasi Putih", 5000, "makanan"));
        menus.add(new Menu("Nasi Merah", 8000, "makanan"));
        menus.add(new Menu("Nasi Uduk", 8000, "makanan"));
        menus.add(new Menu("Nasi Daun Jeruk", 10000, "makanan"));
        menus.add(new Menu("Es Teh Manis", 5000, "minuman"));
        menus.add(new Menu("Teh Manis Hangat", 5000, "minuman"));
        menus.add(new Menu("Es Jeruk", 5000, "minuman"));
        menus.add(new Menu("Jeruk Hangat", 5000, "minuman"));
        menus.add(new Menu("Air Putih", 3000, "minuman"));
        menus.add(new Menu("Air Es", 3000, "minuman"));
        menus.add(new Menu("Es Lemon", 5000, "minuman"));
        menus.add(new Menu("Lemon Hangat", 5000, "minuman"));

        while(true) {
            System.out.println("==== Pecel Lele Cak Mimin ====");
            System.out.println("\nMenu Utama: ");
            System.out.println("1. Pesanan Makanan/Minuman"); //Menu pelanggan
            System.out.println("2. Kelola Menu"); //Menu owner
            System.out.println("3. Keluar");
            System.out.println("Pilih opsi (1-3): ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch(pilihan) {
                case 1:
                    menuPelanggan();
                    break;
                case 2:
                    menuOwner();
                    break;
                case 3:
                    System.out.println("Terima kasih telah berkunjung!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }
    
    //Method menu pelanggan
    private static void menuPelanggan() {
        tampilkanMenu();
        ArrayList<Menu> orderedMenus = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();

        while(true) {
            System.out.println("Masukan nama menu yang ingin dipesan (atau ketik 'selesai' untuk menyelesaikan) :");
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("selesai")) {
                break;
            }
            Menu menu = cariMenu(input);
            if(menu == null) {
                System.out.println("Menu tidak ditemukan. Coba lagi.");
                continue;
            }
            System.out.println("Masukan Jumlah Pesanan :");
            int qty = scanner.nextInt();
            scanner.nextLine();
            if (qty <= 0) {
                System.out.println("Jumlah pesanan harus lebih dari 0. Coba lagi.");
                continue;
            }
            orderedMenus.add(menu);
            quantities.add(qty);
        }
        if (orderedMenus.isEmpty()) {
            System.out.println("Tidak ada pesanan.");
            return;
        }

        double subtotal = hitungSubtotal(orderedMenus, quantities);
        double diskonMinuman = hitungDiskonMinuman(orderedMenus, quantities, subtotal);
        subtotal -= diskonMinuman;
        double diskonPersen = 0;
        if (subtotal > 100000) {
            diskonPersen= 0.1*subtotal;
            subtotal -= diskonPersen;
        }
        double pajak = 0.1*subtotal;
        double biayaPelayanan = 20000;
        double total = subtotal +pajak + biayaPelayanan;

        cetakStruk(orderedMenus, quantities, subtotal + diskonPersen + diskonMinuman, diskonMinuman, diskonPersen, pajak, biayaPelayanan, total);
    }
    //method tampilan menu
    private static void tampilkanMenu() {
        System.out.println("\n === Daftar Menu ===");
        System.out.println("Makanan:");
        for (Menu menu : menus) {
            if (menu.getKategori().equals("makanan")) {
                System.out.println("- " +menu);
            }
        }
    }
    //method search menu by name
    private static Menu cariMenu(String nama) {
        for (Menu menu : menus) {
            if (menu.getNama().equalsIgnoreCase(nama)) {
                return menu;
            }
        }
        return null;
    }
    //method hitung subtotal
    private static double hitungSubtotal(ArrayList<Menu> orderedMenus, ArrayList<Integer> quantities) {
        double total = 0;
        for (int i=0; i<orderedMenus.size(); i++) {
            total += orderedMenus.get(i).getHarga()*quantities.get(i);
        }
        return total;
    }
    //method hitung diskon minuman (beli 1 gratis 1 jika subtotal awal > Rp.50.000)
    private static double hitungDiskonMinuman(ArrayList<Menu> orderedMenus, ArrayList<Integer> quantities, double subtotalAwal) {
        if (subtotalAwal <= 50000) return 0;

        double minHargaMinuman = Double.MAX_VALUE;
        for (int i=0; i<orderedMenus.size(); i++) {
            if(orderedMenus.get(i).getKategori().equals("minuman")) {
                minHargaMinuman = Math.min(minHargaMinuman, orderedMenus.get(i).getHarga());
            }
        }
        return minHargaMinuman == Double.MAX_VALUE ? 0 : minHargaMinuman;
    }
    //method cetak struk
    private static void cetakStruk(ArrayList<Menu> orderedMenus, ArrayList<Integer> quantities, double subtotalSebelumDiskon, double diskonMinuman, double diskonPersen, double pajak, double biayaPelayanan, double total) {
        System.out.println("\n=== Struk Pesanan ===");
        for (int i=0; i<orderedMenus.size(); i++) {
            Menu menu = orderedMenus.get(i);
            int qty = quantities.get(i);
            double hargaPerItem = menu.getHarga();
            double totalPerItem = hargaPerItem*qty;
            System.out.println(menu.getNama() + " x" + qty + " -Rp." +hargaPerItem+ " = Rp." +totalPerItem);
        }
        System.out.println("Subtotal : Rp."+subtotalSebelumDiskon);
        if (diskonMinuman>0) {
            System.out.println("Diskon Minuman (Beli 1 Gratis 1) : - Rp."+diskonMinuman);
        }
        if(diskonPersen>0) {
            System.out.println("Diskon 10% (Subtotal > Rp.100.000) : - Rp."+diskonPersen);
        }
        System.out.println("Pajak (10%) : Rp."+pajak);
        System.out.println("Biaya Pelayanan : Rp."+biayaPelayanan);
        System.out.println("Total : Rp."+total);
    }
    
    //method menu owner
    private static void menuOwner() {
        while(true) {
            System.out.println("\n=== Menu Owner ===");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.println("Pilih opsi (1-4): ");
            int pilihan =scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    ubahHargaMenu();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    //method tambah menu
    private static void tambahMenu() {
        System.out.print("Masukan nama menu: ");
        String nama = scanner.nextLine();
        System.out.print("Masukan harga: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Msukan kategori (makanan/minuman): ");
        String kategori = scanner.nextLine();
        if (!kategori.equals("makanan")&& !kategori.equals("minuman")) {
            System.out.println("Kategori tidak valid.");
            return;
        }
        menus.add(new Menu(nama, harga, kategori));
        System.out.println("Menu berhasil ditambahkan.");
    }

    //method ubah harga menu
    private static void ubahHargaMenu() {
        tampilkanMenuDenganNomor();
        System.out.print("Masukan nomor menu yang ingin diubah harganya: ");
        int nomor = scanner.nextInt();
        scanner.nextLine();
        if (nomor<1 || nomor>menus.size()) {
            System.out.println("Nomer tidak valid.");
            return;
        }
        Menu menu = menus.get(nomor - 1);
        System.out.println("Menu dipilih : " +menu);
        System.out.print("Masukan harga baru: ");
        double hargaBaru = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Apakah anda yakin ingin mengubah harga menu ini? (ya/tidak): ");
        String konfrimasi = scanner.nextLine();
        if (konfrimasi.equalsIgnoreCase("ya")) {
            menu.setHarga(hargaBaru);
            System.out.println("Harga menu berhasil diubah.");
        } else {
            System.out.println("Perubahan harga dibatalkan.");
        }
    }

    //method hapus menu
    private static void hapusMenu() {
        tampilkanMenuDenganNomor();
        System.out.println("Masukan nomer menu yang ingin dihapus: ");
        int nomor = scanner.nextInt();
        scanner.nextLine();
        if (nomor<1 || nomor>menus.size()) {
            System.out.println("Nomer tidak valid.");
            return;
        }
        Menu menu = menus.get(nomor - 1);
        System.out.println("Menu dipilih: "+menu);
        System.out.println("Apakah anda yakin ingin menghapus menu ini? (ya/tidak): ");
        String konfirmasi = scanner.nextLine();
        if (konfirmasi.equalsIgnoreCase("ya")) {
            menus.remove(nomor - 1);
            System.out.println("Menu berhasil dihapus.");
        } else {
            System.out.println("Penghapusan menu dibatalkan.");
        }
    }

    //method menampilkan menu dengan nomor
    private static void tampilkanMenuDenganNomor() {
        System.out.println("\n=== Daftar Menu ===");
        for (int i=0; i<menus.size(); i++) {
            System.out.println((i+1)+". " +menus.get(i));
        }
    }
}
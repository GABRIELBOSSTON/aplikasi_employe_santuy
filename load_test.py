import requests
import time
import json

# KONFIGURASI TARGET
# 1. URL API (Bukan URL halaman depan)
# Cari di Network Tab: biasanya berakhiran /api/chat atau /api/generate
target_url = "https://topgpt-chi.vercel.app/"

# 2. Data Pesan (Payload)
# Ini format data yang dikirim ke 'koki' (Gemini). 
# Harus persis sama dengan yang ada di tab "Payload" atau "Request Body" di browser.
payload = {
    "messages": [
        {"role": "user", "content": "Halo, ini tes load."}
    ]
    # Catatan: Sesuaikan kunci (key) "messages", "role", atau "content" 
    # dengan struktur JSON asli di websitemu.
}

headers = {
    'Content-Type': 'application/json',
    'User-Agent': 'TopGPT-LoadTest/1.0'
}

def hit_server(current_request):
    try:
        # Kita gunakan POST, bukan GET
        response = requests.post(target_url, json=payload, headers=headers)
        
        # Cek hasil
        if response.status_code == 200:
            print(f"✅ Request {current_request}: Sukses! Server merespons.")
        else:
            print(f"⚠️ Request {current_request}: Gagal (Status: {response.status_code})")
            print(f"   Pesan Error: {response.text[:100]}") # Lihat pesan errornya
            
    except Exception as e:
        print(f"❌ Error Koneksi: {e}")

# EKSEKUSI
print("🚀 Memulai API Load Test...")
jumlah_request = 5  # Kita coba 5 dulu untuk memastikan settingan benar

for i in range(jumlah_request):
    hit_server(i + 1)
    time.sleep(1) # Jeda 1 detik agar tidak langsung di-banned Vercel saat tes awal
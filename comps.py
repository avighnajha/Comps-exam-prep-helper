import requests
import os
import io

API_key = "AIzaSyBrMd6RWRY2X1hjRuDOqCJ9zYzhkAwvB3c"
drive_api_url = "https://www.googleapis.com/drive/v3/files"
folder_id = "17HYC_ljs7cEXi1Q3H3dRkuM9FSSYvYs1"



def list_files(folder_id, api_key):
    drive_api_url = "https://www.googleapis.com/drive/v3/files"
    params = {
        "q": f"'{folder_id}' in parents",
        "fields": "files(id, name, mimeType, parents, webViewLink)",
        "key": API_key
    }
    response = requests.get(drive_api_url, params = params)
    if response.status_code == 200:
        data = response.json()
        print(data)
        return data["files"]
    else:
        print("Error: ", response.status_code, response.text)
        return []

list_files(folder_id, API_key)
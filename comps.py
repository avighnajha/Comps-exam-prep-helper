import requests
import os
import io

api_key = "AIzaSyBrMd6RWRY2X1hjRuDOqCJ9zYzhkAwvB3c"
drive_api_url = "https://www.googleapis.com/drive/v3/files"
folder_id = "17HYC_ljs7cEXi1Q3H3dRkuM9FSSYvYs1"
folder_chain = {"A. Pre-2019 Entry Syllabus": "3rd and 4th Year","B. Post-2019 Entry Syllabus":"3rd and 4th year", "3rd and 4th Year": "B. Past Papers", "B. Past Papers": "Questions"}


def list_files(folder_id, api_key):
    drive_api_url = "https://www.googleapis.com/drive/v3/files"
    params = {
        "q": f"'{folder_id}' in parents",
        "fields": "files(id, name, mimeType, parents, webViewLink)",
        "key": api_key
    }
    response = requests.get(drive_api_url, params = params)
    if response.status_code == 200:
        data = response.json()
        print(data)
        return data["files"]
    else:
        print("Error: ", response.status_code, response.text)
        return []

def download_file(file_id, api_key, output_path):
    url = f"https://www.googleapis.com/drive/v3/files/{file_id}?alt=media"
    params = {
        "key": api_key,
    }
    response = requests.get(url, params = params)
    if response.status_code == 200:
        with open(output_path, "wb") as f:
            f.write(response.content)
    else:
        print("Error: ", response.status_code, response.text)

def download_recursive(folder_id,folder_name, api_key, output_folder, folder_chain, next): # Next tells us if the next folder layer is the layer with years or not. If it is we append the year name to the output folder.
    files = list_files(folder_id, api_key)
    if next:
        for file in files:
            if file["name"].find("Comprehensive")!=-1:
                download_file(file["id"], api_key, os.path.join(output_folder, folder_name))
        return
    for file in files:
        if folder_name in folder_chain and file["name"] == folder_chain[folder_name]:
            download_recursive(file["id"], file["name"], api_key, output_folder, folder_chain, next)
        elif folder_name not in folder_chain:
            download_recursive(file["id"], file["name"],api_key, output_folder, folder_chain, True)
    
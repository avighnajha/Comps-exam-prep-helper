import requests
import os
import io
import tkinter as tk
from tkinter import filedialog
import api_keys

api_key = api_keys.drive_api_key
drive_api_url = "https://www.googleapis.com/drive/v3/files"
folder_id = "17HYC_ljs7cEXi1Q3H3dRkuM9FSSYvYs1"
folder_chain = {"A. Pre-2019 Entry Syllabus": "3rd and 4th Year",
                "B. Post-2019 Entry Syllabus":"3rd and 4th year",
                "3rd and 4th Year": "B. Past Papers",
                "B. Past Papers": "Questions",
                "Questions": ""}
question_folder_dis = {"pre": "1eZbRZQnjkv_mKhHEHF71STYwFUGPkUtH", "post": "1azGmV1P-_CWqHTgdCmW8v24VbQffV5MN"}

def list_files(folder_id, api_key):
    #drive_api_url = "https://www.googleapis.com/drive/v3/files"
    params = {
        "q": f"'{folder_id}' in parents",
        "fields": "files(id, name, mimeType, parents, webViewLink)",
        "key": api_key
    }
    response = requests.get(drive_api_url, params = params)
    if response.status_code == 200:
        data = response.json()
        #print(data)
        return data["files"]
    else:
        print("Error: ", response.status_code, response.text)
        return []

def download_file(file_id,folder_name, file_name, api_key, output_path):
    url = f"https://www.googleapis.com/drive/v3/files/{file_id}?alt=media"
    directory = "/COMPS"
    folder_name = "/"+folder_name
    file_name = "/"+ file_name
    param = {
        "Authorization": "Bearer ACCESS_TOKEN",
        "key": api_key,
    }
    response = requests.get(url, params = param)
    if not os.path.exists(output_path+directory):
        os.makedirs(output_path+directory)
        output_path+=directory

    if not os.path.exists(output_path+folder_name):
        os.makedirs(output_path+folder_name)
        output_path+=folder_name

    output_path+=file_name

    if response.status_code == 200:

        if not os.path.exists(output_path+directory):
            os.makedirs(output_path+directory)
        output_path+=directory

        if not os.path.exists(output_path+folder_name):
            os.makedirs(output_path+folder_name)
        output_path+=folder_name

        output_path+=file_name
        

        with open(output_path, "wb") as f:
            f.write(response.content)
    else:
        print("Error: ", response.status_code, response.text)



def select_output_path():
    # Create a root window (it won't be shown)
    root = tk.Tk()
    root.withdraw()  # Hide the root window

    # Ask the user to select a directory
    output_path = filedialog.askdirectory(
        title="Select Output Directory"
    )

    if output_path:
        print(f"Selected Output Path: {output_path}")
        # You can use this path in your script to save files
    else:
        print("No directory selected")

    return output_path


def find_comps(folder_id):
    files = list_files(folder_id, api_key)
    output = select_output_path()
    for folder in files:
        cur_id = folder["id"]

        papers = list_files(cur_id, api_key)
        for paper in papers:
            if "Comprehensive" in paper["name"]:
                print(paper)

                download_file(paper["id"],folder["name"],paper["name"], api_key, output)


#find_files(folder_id, next_folder="A. Pre-2019 Entry Syllabus")
#print(list_files('1rS5ZigLVCVyMDw8VBJmEpkNr8Bi5uvyQ', api_key))

find_comps(question_folder_dis["pre"])

#download_file("1tvzw7D-HVJX998BASAn9QHh7MYDUQh_X", "2019","NEW_DOWNLOAD.pdf",api_key,select_output_path() )

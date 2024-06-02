# python 3.11

import requests

requestHost = "http://localhost:8080/parkit"

headers = {
    "Host": "localhost:8080", 
    "Connection": "keep-alive", 
    "Cache-Control": "max-age=0", 
    "sec-ch-ua": "\"Microsoft Edge\";v=\"117\", \"Not;A=Brand\";v=\"8\", \"Chromium\";v=\"117\"", 
    "sec-ch-ua-mobile": "?0", 
    "sec-ch-ua-platform": "\"Windows\"", 
    "Upgrade-Insecure-Requests": "1", 
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 Edg/117.0.2045.43", 
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7", 
    "Sec-Fetch-Site": "none", 
    "Sec-Fetch-Mode": "navigate", 
    "Sec-Fetch-User": "?1", 
    "Sec-Fetch-Dest": "document", 
    "Accept-Encoding": "gzip, deflate, br", 
    "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6"}

def testHello(para=None): 
    if para is not None: 
        res = requests.get(requestHost + "/user/hello?name=" + para)
    else: 
        res = requests.get(requestHost + "/user/hello")
    print(res.text)

def testEmail(recipient=None, text=None): 
    url = requestHost + "/user/testEmail"
    if recipient is not None: 
        url += "?rec=" + recipient
    if text is not None: 
        url += "?text=" + text
    res = requests.get(url)
    response = res.text
    statusCode = res.status_code
    print(response + '\n' + str(statusCode))

def testRegister(user="arlen", pwd="qwerty", email="zylkxj@163.com"): 
    url = requestHost + "/user/register"
    jsonStream = {
                    "userName": user, 
                    "password": pwd, 
                    "contactEmail": email
                  }
    
    res = requests.post(url, json=jsonStream)
    print(res.text)

    if res.status_code != 200: 
        return
    
    verifyCode = input("\nPlease enter the verification code: ")
    jsonStream = {
                    "userName": user, 
                    "code": verifyCode
    }
    url = requestHost + "/user/registerVerify"
    res = requests.post(url, json=jsonStream)
    print(res.text)

    while res.text != "Registration completed! ": 
        verifyCode = input("\nPlease enter the verification code: ")
        jsonStream = {
                        "userName": user, 
                        "code": verifyCode
        }
        url = requestHost + "/user/registerVerify"
        res = requests.post(url, json=jsonStream)
        print(res.text)

def testForgot(username="test1"): 
    url = requestHost + "/user/forgotPassword"
    jsonStream = {
                    "userName": username
                    }

    res = requests.post(url, json=jsonStream)
    print(res.text) 

    newPass = input("Please enter the new password: ")
    code = input("Please enter the verification code: ")
    url = requestHost + "/user/resetPassword"
    jsonStream = {
                    "userName": username, 
                    "newPass": newPass, 
                    "code": code
                    }
    res = requests.post(url, json=jsonStream)
    print(res.text)

def testResetPassword(username="test1"): 
    url = requestHost + "/user/resetPassword"
    former = input("Please enter the former password: ")
    newPass = input("Please enter the new password: ")
    jsonStream = {
                    "userName": username, 
                    "formerPass": former, 
                    "newPass": newPass
                    }
    res = requests.post(url, json=jsonStream)
    print(res.text)

def testSql(): 
    url = requestHost + "/user/testsql"
    
    res = requests.get(url)

    print(res.text)

def getAllListings(): 
    url = requestHost + "/getAllListings"
    res = requests.get(url)
    print(res.json)

def testListing(): 
    getAllListings()

if __name__ == "__main__": 
    # testHello()
    # testEmail(text="Altered text")
    # username = input("Please enter the username: ")
    # testRegister(usernam  e, "qwerty")
    # testSql()
    # input()
    # testForgot()
    # testResetPassword()
    testListing()
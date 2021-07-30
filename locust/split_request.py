from locust import task, between
from locust.contrib.fasthttp import FastHttpUser
from locust import events

limit = 100
cont = 0


class WebsiteUser(FastHttpUser):

    def increseCount(self):
        global cont
        cont += 1

    def isNotLimitCount(self):
        global cont
        global limit
        return cont <= limit

    @task
    def search_all2(self):
        self.increseCount()
        if self.isNotLimitCount():
            response_request = self.client.post("/resquest", name="01 /resquest")

            while True:
                response_result = self.client.post("/result", json={

                    "value": response_request.text
                }, name="02 /result")

                # print(response.text)

                if response_result.text == 'true':
                    break

        else:
            self.environment.runner.quit()

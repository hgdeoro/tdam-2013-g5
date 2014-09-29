#!/usr/bin/python

import random
import sys
import httplib, urllib

PASSWD = '123456'

URL = '/messages/Sender'

XML = """
<action id="%s" name="register-user">
	<action-detail>
		<user username="%s" password="%s"/>
	</action-detail>
</action>
"""

def send(username):
	msg = XML % (
		random.randint(1111111111, 9999999999),
		username,
		PASSWD,
	)

	print msg

	conn = httplib.HTTPConnection("127.0.0.1:8080")
	conn.request("POST", URL, msg)
	response = conn.getresponse()
	print response.status, response.reason
	data = response.read()
	print data
	conn.close()
	return msg

if __name__ == '__main__':
	username = sys.argv[1]
	send(username)


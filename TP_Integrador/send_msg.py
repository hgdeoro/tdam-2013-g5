#!/usr/bin/python

import random
import sys
import httplib, urllib

PASSWD = '123456'

URL = '/messages/Sender'

SEND_MESSAGE_XML = """
<action id="%s" name="send-message">"
	<action-detail>
		<auth username="%s" key="%s"></auth>"
		<message to="%s"><![CDATA[%s]]></message>
	</action-detail>
</action>
"""

def send(message_from, to):
	message = "Mensaje de %s para %s" % (message_from, to)
	msg = SEND_MESSAGE_XML % (
		random.randint(1111111111, 9999999999),
		message_from,
		PASSWD,
		to,
		message
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
	message_from = sys.argv[1]
	to = sys.argv[2]
	send(message_from, to)


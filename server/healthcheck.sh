#!/bin/bash

{ exec 3<>/dev/tcp/localhost/8080 && echo -e "GET /openapi HTTP/1.1\\r\\nHost: localhost:8080\\r\\nConnection: close\\r\\n\\r\\n" >&3 && cat <&3; } 3<&-

if [ $? -eq 0 ]; then
    exit 0
else
    exit 1
fi


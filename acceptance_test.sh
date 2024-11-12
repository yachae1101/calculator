#!/bin/bash
test $(curl "localhost:8765/?a=1&b=2") -eq 3
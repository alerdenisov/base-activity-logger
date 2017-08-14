# Activity Logger
Base implementation of activity logger. It's a part of Productivity Ledger project.
Support Windows, MacOS and Linux platforms!

## Goal
Collect and send to listeners bunch of information about activity on local computer. Information will be stored in blockchain to receive payment based on report

## Features

### Mouse Tracking
Application is collecting information about mouse movement (distance in pixels), clicks (count per second) and scrolling. 

### Keyboard Tracking
All keyboard events stores as a zone of activity to avoid leaking of private information (such as passwords, private messages and so)

### Application Tracking
Not ready yet. But already works on Windows. Collect name of process and title information
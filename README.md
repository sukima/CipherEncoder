# CipherEncoder

`CipherEncoder` is a GWT based web app that will encode (and decode) text using
some outdated but classic ciphers.

This was an educational Proof-of-concept. I wanted to see if I could write
cipher algorithms and I wanted to learn GWT.

## Ciphers Supported

Ciphers can be added in the future. For now these are what is currently
included:

* Double Box Playfair
* Rot13
* Double Transposition

## Requirements

[GWT SDK][GWT] Version 2.3.0 or higher.

[GWT]: http://code.google.com/webtoolkit/download.html

## Source

The source is available at [GitHub](http://github.com/sukima/CipherEncoder).

You can run / compile using the supplied `CipherEncoder-build` script:

    Usage: CipherEncoder-build [-h|-d|-s|-g <GWT Directory>] [other options]
      -h      This cruft
      -d      Debug mode
      -s      Shell mode
      -g dir  Directory that GWT jar files are in

## License

This code is licensed under the Educational Community License version 1.0

See `COPYING.txt` for more information.

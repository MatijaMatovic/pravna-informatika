interface PDFBlockParams {
  url: string
}

export default function PDFBlock({url}: PDFBlockParams) {
    return (
      <embed
        src={url}
        type="application/pdf"
        width="500pt"
        height="100%"
      ></embed>
    );
  }
  
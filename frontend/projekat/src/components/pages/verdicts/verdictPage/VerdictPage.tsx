import "./VerdictPage.css"
import { useLoaderData } from "react-router-dom";
import { getVerdictXML } from "../../../../services/requestService";
import PDFBlock from "../../../elements/PDFBlock/PDFBlock";
const apiUrl = process.env.REACT_APP_API_ROUTE;


interface LoaderParams {
    params: {
        verdictID: string;
    }
}

interface LoaderResult {
    verdictID: string,
    verdictXML: string,
}

export async function loader({ params: { verdictID } }: LoaderParams): Promise<LoaderResult> {
  const verdictXML = await getVerdictXML(verdictID);
  return { verdictID, verdictXML };
}

export default function VerdictPage() {
  const { verdictID, verdictXML } = useLoaderData() as LoaderResult;
  return (
    <>
      <div className="wrapper">
        <div
          className="html-content"
          dangerouslySetInnerHTML={{ __html: verdictXML }}
        ></div>
        {verdictID && (
          <div className="pdf-content">
            <PDFBlock url={`${apiUrl}documents/verdict/pdf/${verdictID}`} />
          </div>
        )}
      </div>
    </>
  );
}
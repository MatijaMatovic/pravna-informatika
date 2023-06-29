import "./lawsPage.css"

import { useEffect, useState } from "react";
import { getLawXML } from "../../../services/requestService";


export default function LawsPage() {
  const [lawXML, setLawXML] = useState("");

  useEffect(() => {
    if (window.location.href) {
      const elId = window.location.href.split("#")[1];
      setTimeout(() => {
        const element = document.getElementById(elId);
        console.log(element);
        if (element) element.scrollIntoView();
      }, 400);
    }
  }, []);

  useEffect(() => {
    getLawXML()
      .then((data) => {
        setLawXML(data);
      })
      .catch(console.error);
  }, []);

  return (
    <div>
      <h1 className="heading">Zakon</h1>
      <div
            className="html-content"
            dangerouslySetInnerHTML={{ __html: lawXML }}
          >
      </div>
    </div>
  );
}
